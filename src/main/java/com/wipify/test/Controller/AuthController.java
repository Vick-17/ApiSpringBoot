package com.wipify.test.Controller;

import com.wipify.test.model.User;
import com.wipify.test.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @PostMapping("/connexion")
    public ResponseEntity<?> connexion(@RequestBody User user){
        User userIsExisting = userLoginRepository.findByEmail(user.getEmail());

        if (userIsExisting == null) {
            return ResponseEntity.badRequest().body("Utilisateur introuvable");
        }

        if (!userIsExisting.getPassword().equals(user.getPassword())){
            return ResponseEntity.badRequest().body("Mot de passe incorrect");
        }

        String token =  (userIsExisting.getEmail());
        // Générer un jeton d'authentification et le renvoyer
        return ResponseEntity.ok(token);
    }

}
