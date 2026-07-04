package nodrify.inc.octane.profiles.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByEmailQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByIdQuery;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUserId;
import nodrify.inc.octane.profiles.domain.model.queries.GetProfileByUsernameQuery;
import nodrify.inc.octane.profiles.domain.model.valueobjects.EmailAddress;
import nodrify.inc.octane.profiles.domain.model.valueobjects.UserId;
import nodrify.inc.octane.profiles.domain.services.ProfileQueryService;
import nodrify.inc.octane.profiles.interfaces.rest.resources.ProfileResource;
import nodrify.inc.octane.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Operations related to profiles")
public class ProfilesController {
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Get a profile by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileEntity = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a profile by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileByEmail(@PathVariable String email) {
        EmailAddress emailAddress = new EmailAddress(email);
        var getProfileByEmailQuery = new GetProfileByEmailQuery(emailAddress);
        var profile = profileQueryService.handle(getProfileByEmailQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileEntity = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get a profile by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        UserId user = new UserId(userId);
        var getProfileByUserIdQuery = new GetProfileByUserId(user);
        var profile = profileQueryService.handle(getProfileByUserIdQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileEntity = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);
        return ResponseEntity.ok(profileResource);
    }

    @GetMapping("/user")
    @Operation(summary = "Get a profile by authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile found"),
            @ApiResponse(responseCode = "404", description = "Profile not found")})
    public ResponseEntity<ProfileResource> getProfileByAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var getProfileByUsernameQuery = new GetProfileByUsernameQuery(username);
        var profile = profileQueryService.handle(getProfileByUsernameQuery);
        if (profile.isEmpty()) return ResponseEntity.notFound().build();
        var profileEntity = profile.get();
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profileEntity);
        return ResponseEntity.ok(profileResource);
    }

}
