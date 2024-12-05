package lk.ijse.greenshadow.repository;

import lk.ijse.greenshadow.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
