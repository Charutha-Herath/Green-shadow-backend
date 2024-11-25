package lk.ijse.greenshadow.DTO.IMPL;

import com.example.demo.DTO.MonitoringLogStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MonitoringLogDTO implements MonitoringLogStatus {
   @Id
    private String logCode;
    private String logDate;
    private String logDetails;
    private String observedImage;
    private List<String>staffList;
    private List<String> cropList;
    private List<String> fieldList;
}
