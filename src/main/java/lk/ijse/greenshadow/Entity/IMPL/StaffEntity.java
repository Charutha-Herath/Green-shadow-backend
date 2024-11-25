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
@Table(name = "staff")
public class StaffEntity {
    @Id
    private String memberCode;
    private String firstName;
    private String lastName;
    private String joinedDate;
    private String dateOfBirth;
    private String gender;
    private String designation;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;
    private String contactNo;
    @Column(unique = true)
    private String email;
    private String role;
    @OneToMany(mappedBy = "staffEntity",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<staffDetailsEntity> staffEquipmentDetailsList;
    @OneToMany(mappedBy = "staff" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<VehicleEntity> vehicleList;
    @ManyToMany(mappedBy = "staffList" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<FieldEntity> fieldList;
    @ManyToMany(mappedBy = "staffList" ,cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<LogEntity> logList;
}
