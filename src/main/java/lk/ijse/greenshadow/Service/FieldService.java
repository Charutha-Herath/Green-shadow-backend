package lk.ijse.greenshadow.Service;

import lk.ijse.greenshadow.DTO.FieldStatus;
import lk.ijse.greenshadow.DTO.IMPL.FieldDTO;

import java.util.List;

public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    List<FieldDTO>getAllFields();
    FieldStatus getField(String fieldCode);
    void deleteFields(String fieldCode);
    void updateField(String fieldCode, FieldDTO fieldDTO);

}
