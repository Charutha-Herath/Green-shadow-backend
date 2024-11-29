package lk.ijse.greenshadow.Service;



import lk.ijse.greenshadow.DTO.IMPL.MonitoringLogDTO;
import lk.ijse.greenshadow.DTO.MonitoringLogStatus;

import java.util.List;

public interface MonitoringLogService {
    void saveLog(MonitoringLogDTO monitoringLogDTO);
    List<MonitoringLogDTO> getAllLogs();
    MonitoringLogStatus getLog(String logCode);
    void deleteLog(String logCode);
    void updateLog(String logCode,MonitoringLogDTO monitoringLogDTO);
}
