package com.wipify.test.Controller;

import com.wipify.test.model.UserEntity;
import com.wipify.test.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody UserEntity userEntity){
        UserEntity userEntityIsExisting = userLoginRepository.findByEmail(userEntity.getEmail());

        if (userEntityIsExisting == null) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }

        String enteredPassword = userEntity.getPassword();
        String storedPassword = userEntityIsExisting.getPassword();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passwordMatches = encoder.matches(enteredPassword, storedPassword);

        if (!passwordMatches) {
            return ResponseEntity.badRequest().body("Mot de passe incorrect");
        }

        String token = userEntityIsExisting.getEmail();
        // Générer un jeton d'authentification et le renvoyer
        return ResponseEntity.ok(token);
    }


}
