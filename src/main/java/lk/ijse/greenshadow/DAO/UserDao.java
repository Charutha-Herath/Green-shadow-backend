package lk.ijse.greenshadow.DAO;


import lk.ijse.greenshadow.Entity.IMPL.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}
