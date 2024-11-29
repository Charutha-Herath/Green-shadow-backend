package lk.ijse.greenshadow.Util;


import lk.ijse.greenshadow.DTO.IMPL.CropDTO;
import lk.ijse.greenshadow.DTO.IMPL.FieldDTO;
import lk.ijse.greenshadow.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.greenshadow.Entity.IMPL.CropEntity;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Entity.IMPL.LogEntity;
import lk.ijse.greenshadow.Entity.IMPL.StaffEntity;
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

    public CropDTO asCropDtolist(CropEntity crop){
        CropDTO cropDTO =new CropDTO();
        cropDTO.setCropCode(crop.getCropCode());
        cropDTO.setCropName(crop.getCropName());
        cropDTO.setScientificName(crop.getScientificName());
        cropDTO.setCategory(crop.getCategory());
        cropDTO.setSeason(crop.getSeason());
        cropDTO.setCropImage(crop.getCropImage());
        cropDTO.setFieldList(crop.getFieldList().stream().map(FieldEntity::getFieldCode).toList());
        cropDTO.setLogList(crop.getLogList().stream().map(LogEntity::getLogCode).toList());
        System.out.println("CropDTO : "+cropDTO);
        return cropDTO;
    }


    public FieldEntity toFieldEntity(FieldDTO fieldDTO){

        return modelMapper.map(fieldDTO, FieldEntity.class);
    }

    public LogEntity toMonitoringLogEntity(MonitoringLogDTO monitoringLogDTO){
        return modelMapper.map(monitoringLogDTO, LogEntity.class);
    }

    public MonitoringLogDTO toMonitoringLogDto(LogEntity monitoringLog){
        return modelMapper.map(monitoringLog, MonitoringLogDTO.class);
    }

    public MonitoringLogDTO asMonitoringDtoList(LogEntity logEntity){
        MonitoringLogDTO mdt=new MonitoringLogDTO();
        mdt.setLogCode(logEntity.getLogCode());
        mdt.setLogDate(logEntity.getDate());
        mdt.setLogDetails(logEntity.getLogDetails());
        mdt.setObservedImage(logEntity.getObservedImage());
        mdt.setStaffList(logEntity.getStaffList().stream().map(StaffEntity::getMemberCode).toList());
        mdt.setCropList(logEntity.getCropList().stream().map(CropEntity::getCropCode).toList());
        mdt.setFieldList(logEntity.getFieldList().stream().map(FieldEntity::getFieldCode).toList());
        return mdt;
    }
}
