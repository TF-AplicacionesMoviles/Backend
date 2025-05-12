package com.platform.dentify.iam.application.internal.commandservices;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.domain.model.commands.SignInCommand;
import com.platform.dentify.iam.domain.model.commands.SignUpCommand;
import com.platform.dentify.iam.domain.services.AuthCommandService;
import com.platform.dentify.iam.domain.services.JwtService;

import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.interfaces.rest.resources.AuthResponseResource;
import com.platform.dentify.iam.interfaces.rest.resources.RegisterResponseResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCommandServiceImpl implements AuthCommandService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthCommandServiceImpl(JwtService jwtService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseResource login(SignInCommand command) {
        User user = userRepository.findByUsername(command.username()).orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado"));

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        String accessToken = jwtService.generateAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(user.getId()));

        return new AuthResponseResource(accessToken, refreshToken);
    }

    @Override
    public RegisterResponseResource register(SignUpCommand command) {
        if (userRepository.findByUsername(command.username()).isPresent()) {
            throw new IllegalArgumentException("el nombre de usuario ya esta en uso");
        }

        String hashedPassword = passwordEncoder.encode(command.password());

        var user = new User(command, hashedPassword);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("un error ha ocurrido mientras se guarda el usuario" + e.getMessage());
        }

        String accessToken = jwtService.generateAccessToken(String.valueOf(user.getId()));
        String refreshToken = jwtService.generateRefreshToken(String.valueOf(user.getId()));

        return new RegisterResponseResource(accessToken, refreshToken, user.getUsername());
    }



}
