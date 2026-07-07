package nodrify.inc.octane.iam.interfaces.rest.transform;

import nodrify.inc.octane.iam.domain.model.commands.SignUpCommand;
import nodrify.inc.octane.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
    public static SignUpCommand toCommandFromResource(SignUpResource signUpResource) {
        return new SignUpCommand(
                signUpResource.firstName(),
                signUpResource.lastName(),
                signUpResource.username(),
                signUpResource.email(),
                signUpResource.photoUrl(),
                signUpResource.password(),
                signUpResource.roles()
        );
    }
}
