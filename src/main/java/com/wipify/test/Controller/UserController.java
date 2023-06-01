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

import java.util.Optional;
import java.util.UUID;

/**
 * Contrôleur pour les opérations liées aux utilisateurs.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleJpaRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userEntity Le nouvel utilisateur à créer.
     * @param response   La réponse HTTP.
     * @return L'utilisateur créé.
     */
    @PostMapping(value = "/user", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public String createUser(@RequestBody UserEntity userEntity, HttpServletResponse response) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);
        UserEntity savedUser = userRepository.save(userEntity);

        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("Le rôle 'ROLE_USER' n'a pas été trouvé.");
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(savedUser);
        userRoleEntity.setRole(userRole);
        userRoleRepository.save(userRoleEntity);

        String confirmationLink = generateConfirmationLink(savedUser.getId());


        return confirmationLink;
    }

    /**
     * Confirme le compte d'un utilisateur.
     *
     * @param userId L'ID de l'utilisateur.
     * @param token  Le jeton de confirmation.
     * @return Une chaîne indiquant le statut de la confirmation.
     */
    @GetMapping("/confirmation")
    public String confirmAccount(@RequestParam("userId") int userId, @RequestParam("token") String token) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user != null && token.equals(user.getConfimartionLink())) {
                // Mettre à jour le champ is_verified à true
                user.setVerified(true);
                userRepository.save(user);
                return "account_confirmed"; // Afficher une page de confirmation réussie
            } else {
                return "invalid_token"; // Afficher une page d'erreur de token invalide
            }
        } else {
            return "Utilisateur introuvable";
        }
    }

    private String generateConfirmationLink(int userId) {
        String token = UUID.randomUUID().toString();
        String confirmationLink = "http://wipify/confirmation?userId=" + userId + "&token=" + token;
        return confirmationLink;
    }

    /*@PostMapping(value = "/login", consumes = "application/json")
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
