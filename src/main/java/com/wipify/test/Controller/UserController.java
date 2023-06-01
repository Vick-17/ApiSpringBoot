package com.wipify.test.Controller;

import com.wipify.test.model.RoleEntity;
import com.wipify.test.model.UserEntity;
import com.wipify.test.model.UserRoleEntity;
import com.wipify.test.repository.RoleJpaRepository;
import com.wipify.test.repository.UserRepository;
import com.wipify.test.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleJpaRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping(value = "/user", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public UserEntity createUser(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);
        UserEntity savedUser = userRepository.save(userEntity);

        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("le role 'ROLE_USER 'a pas été trouver.");
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(savedUser);
        userRoleEntity.setRole(userRole);

        userRoleRepository.save(userRoleEntity);

        UserRoleEntity userRoleEnt = new UserRoleEntity();
        userRoleEntity.setUser(savedUser);
        userRoleEntity.setRole(userRole);
        userRoleRepository.save(userRoleEnt);

        String confirmationLink = generateConfimartionLink(userEntity.getId());
        response.addHeader("Confirmation-Link", confirmationLink);

        return savedUser;
    }

    private String generateConfimartionLink(int userId){
        String token = UUID.randomUUID().toString();
        String confirmationLink = "http://example.com/confirm?userId=\" + userId + \"&token=\" + token;";

        return confirmationLink;
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
