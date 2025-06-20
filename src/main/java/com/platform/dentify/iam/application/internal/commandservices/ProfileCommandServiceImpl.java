package com.platform.dentify.iam.application.internal.commandservices;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.domain.model.commands.UpdateInformationCommand;
import com.platform.dentify.iam.domain.model.commands.UpdatePasswordCommand;
import com.platform.dentify.iam.domain.model.valueobjects.EmailAddress;
import com.platform.dentify.iam.domain.model.valueobjects.PersonName;
import com.platform.dentify.iam.domain.services.ProfileCommandService;
import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileCommandServiceImpl implements ProfileCommandService {

    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    public ProfileCommandServiceImpl(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Optional<User> handle(UpdateInformationCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();

        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        user.setName(new PersonName(command.firstName(), command.lastName()));
        user.setEmail(new EmailAddress(command.email()));
        user.setCompanyName(command.companyName());

        try {
            userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            throw new IllegalStateException("Could not save user", e);
        }
    }

    @Override
    public Optional<User> handle(UpdatePasswordCommand command) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));
        System.out.println(user.getPassword() + "\n");
        if (!passwordEncoder.matches(command.oldPassword(), user.getPassword())) {
            throw new IllegalStateException("Old password is incorrect");
        }

        String hashedPassword = passwordEncoder.encode(command.newPassword());
        user.setPassword(hashedPassword);

        try {
            var updatedUser = userRepository.save(user);
            return Optional.of(updatedUser);

        } catch (Exception e) {
            throw new IllegalStateException("Could not save user", e);
        }
    }
}
