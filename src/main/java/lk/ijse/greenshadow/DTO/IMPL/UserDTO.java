package lk.ijse.greenshadow.DTO.IMPL;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    @Id
    private String email;
    private String password;
    private String role;
}
