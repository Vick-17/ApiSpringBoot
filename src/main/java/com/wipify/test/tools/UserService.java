package com.wipify.test.tools;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.proc.BadJOSEException;
import com.wipify.test.model.UserEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserEntity save(UserEntity userEntity);
    UserEntity addRoleToUser(String username, String roleName);
    UserEntity findByUsername(String username);
    List<UserEntity> findAll();
    Map<String,String> refreshToken(String authorizationHeader, String issuer) throws BadJOSEException, ParseException, JOSEException;
}
