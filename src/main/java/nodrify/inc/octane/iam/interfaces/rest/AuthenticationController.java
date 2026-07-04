package nodrify.inc.octane.iam.interfaces.rest;

import nodrify.inc.octane.iam.domain.services.UserCommandService;
import nodrify.inc.octane.iam.interfaces.rest.resources.AuthenticatedUserResource;
import nodrify.inc.octane.iam.interfaces.rest.resources.SignInResource;
import nodrify.inc.octane.iam.interfaces.rest.resources.SignUpResource;
import nodrify.inc.octane.iam.interfaces.rest.resources.UserResource;
import nodrify.inc.octane.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import nodrify.inc.octane.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import nodrify.inc.octane.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import nodrify.inc.octane.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/authentication" ,produces= MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Authentication", description = "Authentication operations")
public class AuthenticationController {
    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in user", description = "Sign in user with user name and password")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User signed in successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data"),
                    @ApiResponse(responseCode = "404", description = "Not Found - User does not exist"),
            }
    )
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource){
        var signInCommand= SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);

        var authenticatedUser = userCommandService.handle(signInCommand);

        if (authenticatedUser.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Sign-up", description = "Sign-up with the provided details and credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request.")})
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);

    }
}
