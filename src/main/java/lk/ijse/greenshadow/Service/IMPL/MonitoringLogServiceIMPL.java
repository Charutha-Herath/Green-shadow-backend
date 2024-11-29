package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.CropDao;
import lk.ijse.greenshadow.DAO.FieldDao;
import lk.ijse.greenshadow.DAO.MonitoringLogDao;
import lk.ijse.greenshadow.DAO.StaffDao;
import lk.ijse.greenshadow.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.greenshadow.DTO.MonitoringLogStatus;
import lk.ijse.greenshadow.Entity.IMPL.CropEntity;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Entity.IMPL.LogEntity;
import lk.ijse.greenshadow.Entity.IMPL.StaffEntity;
import lk.ijse.greenshadow.Exception.DataPersistException;
import lk.ijse.greenshadow.Exception.NotFoundException;
import lk.ijse.greenshadow.Service.MonitoringLogService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class MonitoringLogServiceIMPL implements MonitoringLogService {
    @Autowired
    private MonitoringLogDao monitoringLogDao;
    @Autowired
    private Mapping mapping;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CropDao cropDao;
    @Override
    public void saveLog(MonitoringLogDTO monitoringLogDTO) {
        int number=0;
        LogEntity log =monitoringLogDao.findLastRowNative();
        if (log!=null){
            String [] parts=monitoringLogDTO.getLogCode().split("-");
            number=Integer.parseInt(parts[1]);
        }
        monitoringLogDTO.setLogCode("LOG-"+ ++number);
        List<StaffEntity>staffEntities=new ArrayList<>();
        List<FieldEntity>fieldEntities=new ArrayList<>();
        List<CropEntity>cropEntities=new ArrayList<>();
        for (String staffId :monitoringLogDTO.getStaffList()){
            if (staffDao.existsById(staffId)){
                staffEntities.add(staffDao.getReferenceById(staffId));
            }
        }
        for (String cropCode :monitoringLogDTO.getCropList()){
            if (cropDao.existsById(cropCode)){
                cropEntities.add(cropDao.getReferenceById(cropCode));
            }
        }
        for (String fieldCode : monitoringLogDTO.getFieldList()){
            if (fieldDao.existsById(fieldCode)){
                fieldEntities.add(fieldDao.getReferenceById(fieldCode));
            }
        }
        LogEntity log1 =mapping.toMonitoringLogEntity(monitoringLogDTO);
        log1.setFieldList(fieldEntities);
        log1.setStaffList(staffEntities);
        log1.setCropList(cropEntities);

            for (FieldEntity field:fieldEntities){
                field.getLogList().add(log1);
            }
        LogEntity save=monitoringLogDao.save(log1);
            if (save==null){
                throw new DataPersistException("something went wrong");
            }

    }




    @Override
    public List<MonitoringLogDTO> getAllLogs() {

        List<MonitoringLogDTO>monitoringLogDTOS=new ArrayList<>();
        for (LogEntity logEntity :monitoringLogDao.findAll()){
            List<String>crop=new ArrayList<>();
            List<String>field=new ArrayList<>();
            List<String>staff=new ArrayList<>();
            for (FieldEntity fieldEntity:logEntity.getFieldList()){
                field.add(fieldEntity.getFieldCode());
            }
            for (CropEntity cropEntity:logEntity.getCropList()){
                crop.add(cropEntity.getCropCode());
            }
            for (StaffEntity staffEntity:logEntity.getStaffList()){
                staff.add(staffEntity.getMemberCode());
            }
            MonitoringLogDTO mtd=mapping.asMonitoringDtoList(logEntity);
            mtd.setFieldList(field);
            mtd.setStaffList(staff);
            mtd.setCropList(crop);
            monitoringLogDTOS.add(mtd);
        }
        return monitoringLogDTOS;
    }

    @Override
    public MonitoringLogStatus getLog(String logCode) {
        LogEntity findLog =monitoringLogDao.getReferenceById(logCode);
        return mapping.toMonitoringLogDto(findLog);
    }

    @Override
    public void deleteLog(String logCode) {
       if (monitoringLogDao.existsById(logCode)){
           LogEntity logEntity =monitoringLogDao.getReferenceById(logCode);
           List<CropEntity>cropEntities=logEntity.getCropList();
           List<FieldEntity>fieldEntities=logEntity.getFieldList();
           List<StaffEntity>staffEntities=logEntity.getStaffList();

           for (CropEntity crop :cropEntities){
                List<LogEntity>logEntities=crop.getLogList();
                logEntities.remove(logEntity);
           }
           for (StaffEntity staffEntity :staffEntities){
               List<LogEntity>logEntities=staffEntity.getLogList();
               logEntities.remove(logEntity);
           }
           for (FieldEntity field:fieldEntities){
               List<LogEntity>logEntities=field.getLogList();
               logEntities.remove(logEntity);
           }
           logEntity.getCropList().clear();
           logEntity.getStaffList().clear();
           logEntity.getFieldList().clear();

           monitoringLogDao.delete(logEntity);
       }else {
                throw new NotFoundException("You entered ID not found");
       }

    }

    @Override
    public void updateLog(String logCode, MonitoringLogDTO monitoringLogDTO) {
        Optional<LogEntity>logEntity=monitoringLogDao.findById(logCode);
            if (logEntity.isPresent()){
                logEntity.get().setDate(monitoringLogDTO.getLogDate());
                logEntity.get().setLogDetails(monitoringLogDTO.getLogDetails());
                logEntity.get().setObservedImage(monitoringLogDTO.getObservedImage());
                List<FieldEntity>fieldEntities=new ArrayList<>();
                List<CropEntity>cropEntities=new ArrayList<>();
                List<StaffEntity>staffEntities=new ArrayList<>();

                    for (String sid :monitoringLogDTO.getStaffList()){
                        staffEntities.add(staffDao.getReferenceById(sid));
                    }
                    for (String cid:monitoringLogDTO.getCropList()){
                        cropEntities.add(cropDao.getReferenceById(cid));
                    }
                    for (String lid:monitoringLogDTO.getFieldList()){
                        fieldEntities.add(fieldDao.getReferenceById(lid));
                    }

                    logEntity.get().setStaffList(staffEntities);
                    logEntity.get().setCropList(cropEntities);
                    logEntity.get().setFieldList(fieldEntities);

            }
    }
}
