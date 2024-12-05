package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.entity.Vehicle;
import lk.ijse.greenshadow.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles(String sortBy) {
        if (sortBy != null) {
            return vehicleRepository.findAll(Sort.by(sortBy));
        }
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setLicensePlate(vehicleDetails.getLicensePlate());
        vehicle.setVehicleCategory(vehicleDetails.getVehicleCategory());
        vehicle.setFuelType(vehicleDetails.getFuelType());
        vehicle.setType(vehicleDetails.getType());
        vehicle.setStatus(vehicleDetails.getStatus());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }
}
