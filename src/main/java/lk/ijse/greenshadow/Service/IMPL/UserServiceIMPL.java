package lk.ijse.greenshadow.Service.IMPL;


import lk.ijse.greenshadow.DAO.UserDao;
import lk.ijse.greenshadow.DTO.IMPL.UserWithKey;
import lk.ijse.greenshadow.Entity.IMPL.UserEntity;
import lk.ijse.greenshadow.Exception.UserNotFoundException;
import lk.ijse.greenshadow.Service.UserService;
import lk.ijse.greenshadow.Util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserServiceIMPL implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private Mapping mapping;




    @Override
    public UserDetailsService userDetailsService() {
        return username ->
                userDao.findByEmail(username).
                        orElseThrow(()->new UserNotFoundException("User Name Not Found"));
    }

    @Override
    public boolean sendCodeToChangePassword(UserWithKey userWithKey) {
        Optional<UserEntity>byEmail=userDao.findByEmail((userWithKey.getEmail()));
        if (byEmail.isPresent()){
            return true;
        }
        return false;
    }
}
