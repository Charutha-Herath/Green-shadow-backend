package lk.ijse.greenshadow.service;

import lk.ijse.greenshadow.entity.User;
import lk.ijse.greenshadow.repository.RoleRepository;
import lk.ijse.greenshadow.repository.UserRepository;
import lk.ijse.greenshadow.security.jwt.JwtUtils;
import lk.ijse.greenshadow.util.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public String authenticateUser(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    public User registerUser(String username, String email, String password, Set<String> strRoles) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encoder.encode(password));

        Set<lk.ijse.greenshadow.entity.Role> roles = new HashSet<>();

        if (strRoles == null) {
            lk.ijse.greenshadow.entity.Role userRole = roleRepository.findByName(Roles.OTHER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch(role.toUpperCase()) {
                    case "MANAGER":
                        lk.ijse.greenshadow.entity.Role managerRole = roleRepository.findByName(Roles.MANAGER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(managerRole);
                        break;
                    case "ADMINISTRATIVE":
                        lk.ijse.greenshadow.entity.Role adminRole = roleRepository.findByName(Roles.ADMINISTRATIVE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "SCIENTIST":
                        lk.ijse.greenshadow.entity.Role scientistRole = roleRepository.findByName(Roles.SCIENTIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(scientistRole);
                        break;
                    default:
                        lk.ijse.greenshadow.entity.Role otherRole = roleRepository.findByName(Roles.OTHER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(otherRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }
}
