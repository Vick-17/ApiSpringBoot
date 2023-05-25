package com.wipify.test.Controller;

import com.wipify.test.model.User;
import com.wipify.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
@Autowired
private UserRepository userRepository;

    @PostMapping(value = "/user", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public User loginUser(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        } else {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }
    }
}
