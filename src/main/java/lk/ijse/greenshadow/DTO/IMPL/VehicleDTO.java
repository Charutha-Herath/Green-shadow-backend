package lk.ijse.greenshadow.DTO.IMPL;


import jakarta.persistence.Id;
import lk.ijse.greenshadow.DTO.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleDTO implements VehicleStatus {
    @Id
    private String vehicleCode;
    private String licensePlateNumber;
    private String name;
    private String category;
    private String fuelType;
    private String remark;
    private String status;
    private String memberCode;
}
