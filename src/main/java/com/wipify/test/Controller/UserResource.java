package com.wipify.test.Controller;

import com.wipify.test.model.RoleDTO;
import com.wipify.test.model.UserEntity;
import com.wipify.test.tools.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserResource {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserEntity> findByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserEntity> save(@RequestBody UserEntity user) {
        UserEntity userEntity = userService.save(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
                .buildAndExpand(userEntity.getPseudo()).toUriString());
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{username}/addRoleUser")
    public ResponseEntity<?> addRoleToUser(@PathVariable String username, @RequestBody RoleDTO request) {
        UserEntity userEntity = userService.addRoleToUser(username, request.getRoleName());
        return ResponseEntity.ok(userEntity);
    }
}
