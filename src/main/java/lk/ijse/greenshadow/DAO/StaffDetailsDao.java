package lk.ijse.greenshadow.DAO;


import lk.ijse.greenshadow.Entity.IMPL.staffDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDetailsDao extends JpaRepository<staffDetailsEntity,String> {
}
