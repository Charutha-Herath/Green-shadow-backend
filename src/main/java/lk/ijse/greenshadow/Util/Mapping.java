package lk.ijse.greenshadow.Util;


import lk.ijse.greenshadow.DTO.IMPL.CropDTO;
import lk.ijse.greenshadow.Entity.IMPL.CropEntity;
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

}
