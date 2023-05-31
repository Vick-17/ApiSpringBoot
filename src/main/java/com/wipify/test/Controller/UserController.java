package com.wipify.test.Controller;

import com.wipify.test.model.UserEntity;
import com.wipify.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
@Autowired
private UserRepository userRepository;

    @PostMapping(value = "/user", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public UserEntity createUser(@RequestBody UserEntity userEntity){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);
        return userRepository.save(userEntity);
    }

/*    @PostMapping(value = "/login", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public UserEntity loginUser(@RequestBody UserEntity userEntity) {
        UserEntity existingUserEntity = userRepository.findByEmail(userEntity.getEmail());
        if (existingUserEntity != null && existingUserEntity.getPassword().equals(userEntity.getPassword())) {
            return existingUserEntity;
        } else {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }
    }*/
}
