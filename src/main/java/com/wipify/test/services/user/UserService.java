package com.wipify.test.services.user;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.wipify.test.model.RoleEntity;
import com.wipify.test.model.UserEntity;
import com.wipify.test.repository.RoleJpaRepository;
import com.wipify.test.repository.UserJpaRepository;
import com.wipify.test.repository.UserLoginRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements com.wipify.test.tools.UserService, UserDetailsService {


    private static final String USER_NOT_FOUND_MESSAGE = "User with username %s not found";

    private final UserLoginRepository userLoginRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserEntity save(UserEntity user){
        log.info("Saving user {} to the database", user.getPseudo());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userLoginRepository.save(user);
    }


    @Override
    public UserEntity addRoleToUser(String username, String roleName){
        log.info("Adding role {} to user {},", roleName, username);
        UserEntity user = userLoginRepository.findByPseudo(username);
        RoleEntity roleEntity = roleJpaRepository.findByName(roleName);
        user.getRole().add(roleEntity);
        return user;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return null;
    }

    @Override
    public List<UserEntity> findAll() {
        return null;
    }

    @Override
    public Map<String, String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException, ParseException, JOSEException {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userJpaRepository.findByPseudo(username);
        if(user == null) {
            String message = String.format(USER_NOT_FOUND_MESSAGE, username);
            log.error(message);
            throw new UsernameNotFoundException(message);
        } else {
            log.debug("User found in the database: {}", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRole().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new User(user.getPseudo(), user.getPassword(), authorities);
        }
    }
}
