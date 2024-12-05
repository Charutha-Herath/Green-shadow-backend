package lk.ijse.greenshadow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.Set;

@Entity
@Table(name = "fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    private String name;

    // Spatial data can be represented using coordinates or geometrical data types
    private double landSize; // in acres or relevant unit

    private Point location; // Simplified for this example

    private String fieldImage1;

    private String fieldImage2;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private Set<Crop> crops;

    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private Set<Staff> staffs;
}
