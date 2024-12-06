package lk.ijse.greenshadow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crops")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String cropName;

    private String scientificName;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String cropImage;

    private String category;

    private String season;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private Field field;

    // Additional attributes like crop health, etc., can be added here
}
