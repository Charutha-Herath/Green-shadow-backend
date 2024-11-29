package lk.ijse.greenshadow.Service;



import lk.ijse.greenshadow.DTO.IMPL.StaffDTO;
import lk.ijse.greenshadow.DTO.StaffStatus;

import java.util.List;

public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    List<StaffDTO>getAllStaff();
    StaffStatus getStaff(String id);
    void deleteStaff(String id);
    void UpdateStaff(String id, StaffDTO staffDTO);
}
