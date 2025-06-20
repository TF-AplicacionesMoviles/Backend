package com.platform.dentify.iam.application.internal.commandservices;

import com.platform.dentify.iam.domain.model.aggregates.User;
import com.platform.dentify.iam.domain.model.queries.GetUserInformationQuery;
import com.platform.dentify.iam.domain.services.ProfileQueryService;
import com.platform.dentify.iam.infrastructure.repositories.UserRepository;
import com.platform.dentify.iam.infrastructure.security.AuthenticatedUserProvider;
import com.platform.dentify.iam.interfaces.rest.resources.UserInfoResponse;
import org.springframework.stereotype.Service;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {
    private final UserRepository userRepository;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public ProfileQueryServiceImpl(UserRepository userRepository, AuthenticatedUserProvider authenticatedUserProvider) {
        this.userRepository = userRepository;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }


    @Override
    public UserInfoResponse handle(GetUserInformationQuery query) {
        Long userId = authenticatedUserProvider.getCurrentUserId();
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalStateException("User not found"));

        String fullName = user.getName().FullName();

        return new UserInfoResponse(
                fullName, user.getUsername(), user.getEmail().address(), user.getCompanyName()
        );

    }
}
