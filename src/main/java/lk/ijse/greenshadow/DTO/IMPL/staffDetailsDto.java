package lk.ijse.greenshadow.DTO.IMPL;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class staffDetailsDto {
    @Id
    private String id;
    private int userEquipmentCount;
    private  StaffDTO staffEntity;
    private  EquipmentDTO equipmentEntity;
}
