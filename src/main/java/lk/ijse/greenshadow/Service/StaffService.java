package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.entity.Staff;
import lk.ijse.greenshadow.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff(String sortBy) {
        if (sortBy != null) {
            return staffRepository.findAll(Sort.by(sortBy));
        }
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(Long id, Staff staffDetails) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        staff.setFirstName(staffDetails.getFirstName());
        staff.setLastName(staffDetails.getLastName());
        staff.setDesignation(staffDetails.getDesignation());
        staff.setGender(staffDetails.getGender());
        staff.setJoinedDate(staffDetails.getJoinedDate());
        staff.setDOB(staffDetails.getDOB());
        staff.setAddressLine1(staffDetails.getAddressLine1());
        staff.setAddressLine2(staffDetails.getAddressLine2());
        staff.setAddressLine3(staffDetails.getAddressLine3());
        staff.setAddressLine4(staffDetails.getAddressLine4());
        staff.setAddressLine5(staffDetails.getAddressLine5());
        staff.setContact(staffDetails.getContact());
        staff.setEmail(staffDetails.getEmail());

        return staffRepository.save(staff);
    }

    public void deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found"));
        staffRepository.delete(staff);
    }
}
