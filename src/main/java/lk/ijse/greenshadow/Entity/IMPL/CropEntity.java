package lk.ijse.greenshadow.Entity.IMPL;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class CropEntity {
    @Id
    private String cropCode;
    private String cropName;
    private String scientificName;
    private String category;
    private String season;
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;

    @ManyToMany(mappedBy = "cropList")
    private List<LogEntity> logList;

    @ManyToMany(mappedBy = "cropList")
    private List<FieldEntity> fieldList;
}
