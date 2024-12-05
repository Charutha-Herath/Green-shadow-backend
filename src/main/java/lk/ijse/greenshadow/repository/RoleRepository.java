package lk.ijse.greenshadow.repository;

import lk.ijse.greenshadow.util.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<lk.ijse.greenshadow.entity.Role, Long> {
    Optional<lk.ijse.greenshadow.entity.Role> findByName(Roles name);
}
