package com.wipify.test.tools;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.wipify.test.model.User;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    User save(User user);
    User addRoleToUser(String username, String roleName);
    User findByUsername(String username);
    List<User> findAll();
    Map<String,String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException, ParseException, JOSEException;
}
