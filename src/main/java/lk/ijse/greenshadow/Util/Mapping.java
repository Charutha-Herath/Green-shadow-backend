package lk.ijse.greenshadow.Util;


import lk.ijse.greenshadow.DTO.IMPL.CropDTO;
import lk.ijse.greenshadow.Entity.IMPL.CropEntity;
import lk.ijse.greenshadow.Entity.IMPL.FieldEntity;
import lk.ijse.greenshadow.Entity.IMPL.LogEntity;
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

}
