package com.platform.dentify.iam.domain.services;

import com.platform.dentify.iam.domain.model.queries.GetUserInformationQuery;
import com.platform.dentify.iam.interfaces.rest.resources.UserInfoResponse;

public interface ProfileQueryService {
    UserInfoResponse handle(GetUserInformationQuery query);
}
