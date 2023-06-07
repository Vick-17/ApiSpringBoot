package com.wipify.test.Controller;

import com.wipify.test.model.RoleEntity;
import com.wipify.test.model.UserEntity;
import com.wipify.test.model.UserRoleEntity;
import com.wipify.test.repository.RoleJpaRepository;
import com.wipify.test.repository.UserRepository;
import com.wipify.test.repository.UserRoleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Value("${serveur.url}")
    private String url;


    /**
     * Crée un nouvel utilisateur.
     *
     * @param userEntity Le nouvel utilisateur à créer.
     * @param response   La réponse HTTP.
     * @return L'utilisateur créé.
     */
    @PostMapping(value = "/user", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public String createUser(@RequestPart("photo") MultipartFile photo, @RequestPart("userEntity") UserEntity userEntity, HttpServletResponse response)
    {
        String photoFilename = photo.getOriginalFilename();
        String photoFilePath = "/resources/img/" + photoFilename;

        try {
            Files.write(Paths.get(photoFilePath), photo.getBytes());
            // Ajoutez ici la logique pour enregistrer le chemin du fichier photo dans l'objet UserEntity
            userEntity.setPhotoPath(photoFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de la photo : " + e.getMessage());
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwordEncode = bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(passwordEncode);
        UserEntity savedUser = userRepository.save(userEntity);
        String token = generateConfirmationToken();
        String confirmationLink = generateConfirmationLink(savedUser.getId(), token);
        RoleEntity userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new RuntimeException("Le rôle 'ROLE_USER' n'a pas été trouvé.");
        }

        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setUser(savedUser);
        userRoleEntity.setRole(userRole);
        userRoleRepository.save(userRoleEntity);

        userEntity.setConfirmationToken(token);
        userRepository.save(userEntity);

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
    public ResponseEntity<String> confirmAccount(@RequestParam("userId") int userId, @RequestParam("token") String token) {
        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (token.equals(user.getConfirmationToken())) {
                // Mettre à jour le champ is_verified à true
                user.setVerified(true);
                userRepository.save(user);
                return ResponseEntity.ok("Verification réussie");
            } else {
                return ResponseEntity.badRequest().body("Token invalide");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }
    }


    private String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }

    private String generateConfirmationLink(int userId, String token) {
        return url + "/confirmation?userId=" + userId + "&token=" + token;
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
