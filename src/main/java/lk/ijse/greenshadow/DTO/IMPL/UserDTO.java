package lk.ijse.greenshadow.DTO.IMPL;

import jakarta.persistence.Id;
import lk.ijse.greenshadow.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    @Id
    private String email;
    private String password;
    private Role role;
}
