package lk.ijse.greenshadow.DTO.IMPL;

import com.example.demo.DTO.CropStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CropDTO implements CropStatus {
   @Id
   private String cropCode;
   private String cropName;
   private String scientificName;
   private String category;
   private String season;
   private String cropImage;
   @JsonIgnore
    private List<String>logList;
   @JsonIgnore
   private List<String> fieldList;




}
