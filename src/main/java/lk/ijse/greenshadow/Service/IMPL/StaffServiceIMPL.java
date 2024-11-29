package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.*;
import lk.ijse.greenshadow.DTO.IMPL.StaffDTO;
import lk.ijse.greenshadow.DTO.StaffStatus;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Entity.IMPL.LogEntity;
import lk.ijse.greenshadow.Entity.IMPL.StaffEntity;
import lk.ijse.greenshadow.Entity.IMPL.VehicleEntity;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Exception.NotFoundException;
import lk.ijse.greenshadow.Service.StaffService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StaffServiceIMPL implements StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private StaffDetailsDao staffDetailsDao;
    @Override
    public void saveStaff(StaffDTO staffDTO) {
        int number =0;
        StaffEntity staff =staffDao.findLastRowNative();
        if (staff!=null){
            String [] parts=staff.getMemberCode().split("-");
            number=Integer.parseInt(parts[1]);
        }
        staffDTO.setMemberCode("MEMBER-"+ ++number);
        List<FieldEntity>fieldEntities =new ArrayList<>();
            for (String fieldCode : staffDTO.getFieldList()){
                fieldEntities.add(fieldDao.getReferenceById(fieldCode));
            }
        List<VehicleEntity>vehicleEntities=new ArrayList<>();
            for (String id :staffDTO.getVehicleList()){
                vehicleEntities.add(vehicleDao.getReferenceById(id));
            }
        List<LogEntity>logEntities=new ArrayList<>();
            for (String lid :staffDTO.getLogList()){
                logEntities.add(monitoringLogDao.getReferenceById(lid));
            }
        StaffEntity staffEntity=mapping.toStaffEntity(staffDTO);
        staffEntity.setFieldList(fieldEntities);
        staffEntity.setVehicleList(vehicleEntities);
        staffEntity.setLogList(logEntities);

        for (FieldEntity field:fieldEntities){
            field.getStaffList().add(staffEntity);
        }
            StaffEntity staffEntity1 =staffDao.save(staffEntity);
            if (staffEntity1==null){
                throw new DataPersistException("Something went wrong");
            }

    }

    @Override
    public List<StaffDTO> getAllStaff() {

        List<StaffDTO>staffDTOS=new ArrayList<>();
        for (StaffEntity staffEntity: staffDao.findAll()){
            List<String>vehicle=new ArrayList<>();
            List<String>field=new ArrayList<>();
            List<String>log=new ArrayList<>();

            for (VehicleEntity vehicleEntity:staffEntity.getVehicleList()){
                vehicle.add(vehicleEntity.getVehicleCode());
            }
            for (FieldEntity fieldEntity:staffEntity.getFieldList()){
                field.add(fieldEntity.getFieldCode());
            }
            for (LogEntity logEntity:staffEntity.getLogList()){
                log.add(logEntity.getLogCode());
            }
            StaffDTO staffDTO=mapping.asStafDtoList(staffEntity);
            staffDTO.setFieldList(field);
            staffDTO.setVehicleList(vehicle);
            staffDTO.setLogList(log);
            staffDTOS.add(staffDTO);
        }
        return staffDTOS;
    }

    @Override
    public StaffStatus getStaff(String id) {
        StaffEntity searchStaff =staffDao.getReferenceById(id);
        return mapping.toStaffDto(searchStaff);
    }



    @Override
    public void UpdateStaff(String id, StaffDTO staffDTO) {
        Optional<StaffEntity>staffEntity=staffDao.findById(id);
            if (staffEntity.isPresent()){
                staffEntity.get().setFirstName(staffDTO.getFirstName());
                staffEntity.get().setLastName(staffDTO.getLastName());
                staffEntity.get().setJoinedDate(staffDTO.getJoinedDate());
                staffEntity.get().setDateOfBirth(staffDTO.getDateOfBirth());
                staffEntity.get().setGender(staffDTO.getGender());
                staffEntity.get().setDesignation(staffDTO.getDesignation());
                staffEntity.get().setAddressLine1(staffDTO.getAddressLine1());
                staffEntity.get().setAddressLine2(staffDTO.getAddressLine2());
                staffEntity.get().setAddressLine3(staffDTO.getAddressLine3());
                staffEntity.get().setAddressLine4(staffDTO.getAddressLine4());
                staffEntity.get().setAddressLine5(staffDTO.getAddressLine5());
                staffEntity.get().setContactNo(staffDTO.getContactNo());
                staffEntity.get().setRole(staffDTO.getRole());
                List<VehicleEntity>vehicleEntities=new ArrayList<>();
                List<FieldEntity>fieldEntities=new ArrayList<>();
                List<LogEntity>logEntities=new ArrayList<>();
                    for (String vehiId :staffDTO.getVehicleList()){
                        vehicleEntities.add(vehicleDao.getReferenceById(vehiId));
                    }
                    for (String logId :staffDTO.getLogList()){
                        logEntities.add(monitoringLogDao.getReferenceById(logId));
                    }
                    for (String fid :staffDTO.getFieldList()){
                        fieldEntities.add(fieldDao.getReferenceById(fid));
                    }
                    staffEntity.get().setVehicleList(vehicleEntities);
                    staffEntity.get().setFieldList(fieldEntities);
                    staffEntity.get().setLogList(logEntities);

            }

    }

    @Override
    public void deleteStaff(String id) {
        if (staffDao.existsById(id)){
            StaffEntity staffEntity =staffDao.getReferenceById(id);
            List<FieldEntity>fieldEntities=staffEntity.getFieldList();
            List<VehicleEntity>vehicleEntities=staffEntity.getVehicleList();
            List<LogEntity>logEntities=staffEntity.getLogList();

            for (FieldEntity field:fieldEntities){
                List<StaffEntity>staffEntities=field.getStaffList();
                staffEntities.remove(staffEntity);
            }
            for (VehicleEntity vehicle:vehicleEntities){
                vehicle.setStaff(null);
            }
            for (LogEntity logEntity:logEntities){
                List<StaffEntity>staffEntities=logEntity.getStaffList();
                staffEntities.remove(staffEntity);
            }
            staffEntity.getFieldList().clear();
            staffEntity.getVehicleList().clear();
            staffEntity.getLogList().clear();

            staffDao.delete(staffEntity);
        }else {
            throw new NotFoundException("Wrong Id");
        }
    }
}
