package lk.ijse.greenshadow.Service;


import lk.ijse.greenshadow.DTO.IMPL.UserWithKey;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserDetailsService userDetailsService();
    boolean sendCodeToChangePassword(UserWithKey userWithKey);
}
