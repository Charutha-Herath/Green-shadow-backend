package lk.ijse.greenshadow.DAO;

import lk.ijse.greenshadow.Entity.IMPL.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogDao extends JpaRepository <LogEntity,String> {
    @Query(value = "SELECT * FROM logs WHERE log_code = (SELECT log_code FROM logs ORDER BY CAST(SUBSTRING(log_code, 5) AS UNSIGNED) DESC LIMIT 1);", nativeQuery = true)
    LogEntity findLastRowNative();
}
