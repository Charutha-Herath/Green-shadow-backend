package lk.ijse.greenshadow.repository;

import lk.ijse.greenshadow.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CropRepository extends JpaRepository<Crop, Long> {
}
