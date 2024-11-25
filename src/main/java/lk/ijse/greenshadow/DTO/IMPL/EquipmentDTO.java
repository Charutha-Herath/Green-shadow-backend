package lk.ijse.greenshadow.DTO.IMPL;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EquipmentDTO {
   @Id
    private String equipmentCode;
   private String name;
   private String type;
   private String status;
   private int availableCount;
   private List<staffDetailsDto> staffEquipmentDetailsList;
   private List<String> fieldList;
}
