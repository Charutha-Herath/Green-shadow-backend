package lk.ijse.greenshadow.controller;

import jakarta.validation.Valid;
import lk.ijse.greenshadow.dto.JwtResponse;
import lk.ijse.greenshadow.dto.LoginRequest;
import lk.ijse.greenshadow.dto.MessageResponse;
import lk.ijse.greenshadow.dto.SignupRequest;
import lk.ijse.greenshadow.entity.User;
import lk.ijse.greenshadow.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("api/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token, "Bearer"));
    }

    @PostMapping("api/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = authService.registerUser(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword(),
                signUpRequest.getRole());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
