package com.platform.dentify.iam.domain.services;

import com.platform.dentify.iam.interfaces.rest.resources.UserInfoResponse;

public interface ProfileQueryService {
    UserInfoResponse getCurrentUserProfile();
}
