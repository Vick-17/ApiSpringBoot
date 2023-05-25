package com.wipify.test.Controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.wipify.test.model.RoleEntity;
import com.wipify.test.model.User;
import com.wipify.test.repository.RoleJpaRepository;
import com.wipify.test.repository.UserLoginRepository;
import com.wipify.test.tools.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with username %s not found";

    private final UserLoginRepository userLoginRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User save(User user){
        log.info("Saving user {} to the database", user.getPseudo());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userLoginRepository.save(user);
    }


    @Override
    public User addRoleToUser(String username, String roleName){
        log.info("Adding role {} to user {},", roleName, username);
        User user = userLoginRepository.findByUsername(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        user.getRoles().add(roleEntity);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Map<String, String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException, ParseException, JOSEException {
        return null;
    }
}
