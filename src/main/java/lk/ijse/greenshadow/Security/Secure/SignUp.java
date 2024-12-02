package lk.ijse.greenshadow.Security.Secure;

import jakarta.validation.constraints.NotNull;
import lk.ijse.greenshadow.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SignUp {
    @NotNull(message = "email cannot be null")
    private String email;

    @NotNull
    private String password;
    @NotNull
    private Role role;
}
