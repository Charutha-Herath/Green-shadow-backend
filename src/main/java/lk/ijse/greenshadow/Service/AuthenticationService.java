package lk.ijse.greenshadow.Service;


import lk.ijse.greenshadow.DTO.IMPL.PasswordDto;
import lk.ijse.greenshadow.Security.Responce.JWTAuthResponse;
import lk.ijse.greenshadow.Security.Secure.SignIn;
import lk.ijse.greenshadow.Security.Secure.SignUp;

public interface AuthenticationService {
    JWTAuthResponse signUp(SignUp signUp);
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse refreshToken(String refreshToken);
    void changePassword(PasswordDto passwordDto);
}
