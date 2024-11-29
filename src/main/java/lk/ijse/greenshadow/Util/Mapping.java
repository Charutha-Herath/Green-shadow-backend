package lk.ijse.greenshadow.Util;


import lk.ijse.greenshadow.DTO.IMPL.CropDTO;
import lk.ijse.greenshadow.DTO.IMPL.FieldDTO;
import lk.ijse.greenshadow.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.greenshadow.DTO.IMPL.StaffDTO;
import lk.ijse.greenshadow.Entity.IMPL.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;


    public CropEntity toCropEntity(CropDTO cropDTO) {
        CropEntity map = modelMapper.map(cropDTO, CropEntity.class);
        return map;
    }

    public CropDTO asCropDtolist(CropEntity crop) {
        CropDTO cropDTO = new CropDTO();
        cropDTO.setCropCode(crop.getCropCode());
        cropDTO.setCropName(crop.getCropName());
        cropDTO.setScientificName(crop.getScientificName());
        cropDTO.setCategory(crop.getCategory());
        cropDTO.setSeason(crop.getSeason());
        cropDTO.setCropImage(crop.getCropImage());
        cropDTO.setFieldList(crop.getFieldList().stream().map(FieldEntity::getFieldCode).toList());
        cropDTO.setLogList(crop.getLogList().stream().map(LogEntity::getLogCode).toList());
        System.out.println("CropDTO : " + cropDTO);
        return cropDTO;
    }


    public FieldEntity toFieldEntity(FieldDTO fieldDTO) {

        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    public LogEntity toMonitoringLogEntity(MonitoringLogDTO monitoringLogDTO) {
        return modelMapper.map(monitoringLogDTO, LogEntity.class);
    }

    public MonitoringLogDTO toMonitoringLogDto(LogEntity monitoringLog) {
        return modelMapper.map(monitoringLog, MonitoringLogDTO.class);
    }

    public MonitoringLogDTO asMonitoringDtoList(LogEntity logEntity) {
        MonitoringLogDTO mdt = new MonitoringLogDTO();
        mdt.setLogCode(logEntity.getLogCode());
        mdt.setLogDate(logEntity.getDate());
        mdt.setLogDetails(logEntity.getLogDetails());
        mdt.setObservedImage(logEntity.getObservedImage());
        mdt.setStaffList(logEntity.getStaffList().stream().map(StaffEntity::getMemberCode).toList());
        mdt.setCropList(logEntity.getCropList().stream().map(CropEntity::getCropCode).toList());
        mdt.setFieldList(logEntity.getFieldList().stream().map(FieldEntity::getFieldCode).toList());
        return mdt;
    }

    public StaffEntity toStaffEntity(StaffDTO staffDTO) {
        return modelMapper.map(staffDTO, StaffEntity.class);
    }


    public StaffDTO asStafDtoList(StaffEntity staffEntity) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setMemberCode(staffEntity.getMemberCode());
        staffDTO.setFirstName(staffEntity.getFirstName());
        staffDTO.setLastName(staffEntity.getLastName());
        staffDTO.setJoinedDate(staffEntity.getJoinedDate());
        staffDTO.setDateOfBirth(staffEntity.getDateOfBirth());
        staffDTO.setGender(staffEntity.getGender());
        staffDTO.setDesignation(staffEntity.getDesignation());
        staffDTO.setAddressLine1(staffEntity.getAddressLine1());
        staffDTO.setAddressLine2(staffEntity.getAddressLine2());
        staffDTO.setAddressLine3(staffEntity.getAddressLine3());
        staffDTO.setAddressLine4(staffEntity.getAddressLine4());
        staffDTO.setAddressLine5(staffEntity.getAddressLine5());
        staffDTO.setContactNo(staffEntity.getContactNo());
        staffDTO.setEmail(staffEntity.getEmail());
        staffDTO.setRole(staffEntity.getRole());
        staffDTO.setVehicleList(staffEntity.getVehicleList().stream().map(VehicleEntity::getVehicleCode).toList());
        staffDTO.setFieldList(staffEntity.getFieldList().stream().map(FieldEntity::getFieldCode).toList());
        staffDTO.setLogList(staffEntity.getLogList().stream().map(LogEntity::getLogCode).toList());
        return staffDTO;
    }



    public StaffDTO toStaffDto(StaffEntity staffEntity){
        return modelMapper.map(staffEntity, StaffDTO.class);
    }
}