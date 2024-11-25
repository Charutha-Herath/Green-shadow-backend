package lk.ijse.greenshadow.Entity.IMPL;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String Name;
    private String category;
    private String fuelType;
    private String remark;
    private String status;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "memberCode")
    private StaffEntity staff;
}
