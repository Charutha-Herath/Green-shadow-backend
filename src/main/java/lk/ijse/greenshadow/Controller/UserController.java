package lk.ijse.greenshadow.Controller;


import lk.ijse.greenshadow.Security.Responce.JWTAuthResponse;
import lk.ijse.greenshadow.Security.Secure.SignIn;
import lk.ijse.greenshadow.Security.Secure.SignUp;
import lk.ijse.greenshadow.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin
public class UserController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthResponse>signIN(@RequestBody SignIn signIn){
        System.out.println("sign ekta awa ========================================");
        return ResponseEntity.ok(authenticationService.signIn(signIn));
    }
    @PostMapping("/signUp")
    public ResponseEntity<JWTAuthResponse> saveUser(@RequestBody SignUp signUp){
        return ResponseEntity.ok(authenticationService.signUp(signUp));
    }
}
