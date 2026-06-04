package dev.zanda.taskmanagerapi.controllers;

import dev.zanda.taskmanagerapi.dto.LoginRequest;
import dev.zanda.taskmanagerapi.dto.RegisterRequest;
import dev.zanda.taskmanagerapi.models.User;
import dev.zanda.taskmanagerapi.repositories.UserRepository;
import dev.zanda.taskmanagerapi.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        try {
            // Tenta l'autenticazione con l'AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Se l'autenticazione ha successo, genera il token JWT
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(loginRequest.getUsername());
            } else {
                // Questo blocco teoricamente non dovrebbe essere raggiunto se authenticate() non lancia eccezioni
                return "Authentication failed";
            }
        } catch (AuthenticationException e) {
            // Gestisce le eccezioni di autenticazione (es. credenziali errate)
            return "Invalid username or password";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        // Controlla se l'utente esiste già
        if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            return "Username already taken";
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        // Codifica la password prima di salvarla
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }
}
