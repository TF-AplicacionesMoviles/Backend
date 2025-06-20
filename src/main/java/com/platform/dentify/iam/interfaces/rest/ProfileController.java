package com.platform.dentify.iam.interfaces.rest;


import com.platform.dentify.iam.domain.model.queries.GetUserInformationQuery;
import com.platform.dentify.iam.domain.services.ProfileCommandService;
import com.platform.dentify.iam.domain.services.ProfileQueryService;
import com.platform.dentify.iam.interfaces.rest.resources.UpdateInformationRequest;
import com.platform.dentify.iam.interfaces.rest.resources.UpdatePasswordRequest;
import com.platform.dentify.iam.interfaces.rest.resources.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.platform.dentify.iam.interfaces.rest.transform.ProfileCommandFromResourceAssembler.toCommand;

@RestController
@Tag(name = "Profile", description = "Profile Endpoint")
@RequestMapping(value ="/api/v1/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }


    @GetMapping
    @Operation(summary = "get user info authenticated")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "user found"),
            @ApiResponse(responseCode = "404", description = "user not found")
    })
    public ResponseEntity<UserInfoResponse> getProfile() {
        return ResponseEntity.ok(profileQueryService.handle(new GetUserInformationQuery()));
    }

    @PutMapping("/update-information")
    @Operation(summary = "update user info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated info successfully"),
            @ApiResponse(responseCode = "400", description = "error updating user info")
    })
    public ResponseEntity<Map<String,String>> updateInformation(@RequestBody UpdateInformationRequest request) {
        profileCommandService.handle(toCommand(request));
        return ResponseEntity.ok(Map.of("message","Information has been updated"));
    }

    @PutMapping("/update-password")
    @Operation(summary = "update password user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated password successfully"),
            @ApiResponse(responseCode = "400", description = "error updating user password")
    })
    public ResponseEntity<Map<String,String>> updatePassword(@RequestBody UpdatePasswordRequest request) {
        profileCommandService.handle(toCommand(request));
        return ResponseEntity.ok(Map.of("message","Password has been updated"));
    }

}
