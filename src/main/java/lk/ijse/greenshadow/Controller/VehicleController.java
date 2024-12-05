package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.entity.Vehicle;
import lk.ijse.greenshadow.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam(required = false) String sortBy) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles(sortBy);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE', 'SCIENTIST', 'OTHER')")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        return ResponseEntity.ok(createdVehicle);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicleDetails) {
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMINISTRATIVE')")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(new MessageResponse("Vehicle deleted successfully!"));
    }
}
