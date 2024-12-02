package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.UserDao;
import lk.ijse.greenshadow.DTO.IMPL.PasswordDto;
import lk.ijse.greenshadow.DTO.IMPL.UserDTO;
import lk.ijse.greenshadow.Entity.IMPL.UserEntity;
import lk.ijse.greenshadow.Exception.NotFoundException;
import lk.ijse.greenshadow.Security.Responce.JWTAuthResponse;
import lk.ijse.greenshadow.Security.Secure.SignIn;
import lk.ijse.greenshadow.Security.Secure.SignUp;
import lk.ijse.greenshadow.Service.AuthenticationService;
import lk.ijse.greenshadow.Service.JWTService;
import lk.ijse.greenshadow.Util.Mapping;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceIMPL implements AuthenticationService {
    private final Mapping mapping;
    private final UserDao userDao;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signUp(SignUp signUp) {
        UserDTO userDTO =UserDTO.builder()
                .email(signUp.getEmail())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .role(signUp.getRole())
                .build();
        UserEntity userEntity1 = mapping.toUserEntity(userDTO);
        System.out.println(userEntity1);
        userDao.save(userEntity1);
        System.out.println(userEntity1);
        String generateToken = jwtService.generateToken(userEntity1);
        return JWTAuthResponse.builder().tokens(generateToken).build();
    }

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(),signIn.getPassword())
        );
        UserEntity userEntity=userDao.findByEmail(signIn.getEmail())
                .orElseThrow(()->new NotFoundException("User Not Found"));
        var generateToken =jwtService.generateToken(userEntity);
        return JWTAuthResponse.builder().tokens(generateToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String refreshToken) {
       String user =jwtService.extractUserName(refreshToken);
       UserEntity findUser =userDao.findByEmail(user).orElseThrow(()-> new NotFoundException("Couldn't find User"));
       String token =jwtService.refreshToken(findUser);
       return JWTAuthResponse.builder().tokens(token).build();
    }

    @Override
    public void changePassword(PasswordDto passwordDto) {
        Optional<UserEntity> byEmail =userDao.findByEmail(passwordDto.getEmail());
            if (byEmail.isPresent()){
                UserEntity userEntity=userDao.getReferenceById(byEmail.get().getUsername());
                userEntity.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
                userDao.save(userEntity);
            }
    }
}
