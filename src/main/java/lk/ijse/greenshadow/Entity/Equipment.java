package lk.ijse.greenshadow.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lk.ijse.greenshadow.util.EquipmentType;
import lk.ijse.greenshadow.util.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "equipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private EquipmentType equipmentType;

    private Status status;

    @ManyToMany
    @JoinTable(name = "equipment_field",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id"))
    private Set<Field> fields;

    @ManyToMany
    @JoinTable(name = "equipment_staff",
            joinColumns = @JoinColumn(name = "equipment_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id"))
    private Set<Staff> staffs;
}
