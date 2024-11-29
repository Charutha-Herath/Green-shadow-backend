package lk.ijse.greenshadow.Service;



import lk.ijse.greenshadow.DTO.IMPL.VehicleDTO;
import lk.ijse.greenshadow.DTO.VehicleStatus;

import java.util.List;

public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    List<VehicleDTO>getAllVehicles();
    VehicleStatus getVehicle(String vehicleCode);
    void deleteVehicle(String vehicleCode);
    void updateVehicle(String vehicleCode, VehicleDTO vehicleDTO);
}
