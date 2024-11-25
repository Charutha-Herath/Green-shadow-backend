package lk.ijse.greenshadow.DAO;

import lk.ijse.greenshadow.Entity.IMPL.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffDao extends JpaRepository<StaffEntity,String> {
    @Query(value = "SELECT * FROM staff WHERE member_code = (SELECT member_code FROM staff ORDER BY CAST(SUBSTRING(member_code, 8) AS UNSIGNED) DESC LIMIT 1);", nativeQuery = true)
   StaffEntity findLastRowNative();
}
