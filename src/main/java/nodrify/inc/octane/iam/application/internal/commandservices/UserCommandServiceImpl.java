package nodrify.inc.octane.iam.application.internal.commandservices;

import nodrify.inc.octane.iam.domain.model.commands.DeleteUserCommand;
import nodrify.inc.octane.iam.domain.model.commands.SignInCommand;
import nodrify.inc.octane.iam.domain.model.commands.SignUpCommand;
import nodrify.inc.octane.iam.domain.model.commands.UpdateUserCommand;
import nodrify.inc.octane.shared.application.internal.outboundservices.acl.ExternalProfileService;
import nodrify.inc.octane.iam.application.internal.outboundservices.hashing.HashingService;
import nodrify.inc.octane.iam.application.internal.outboundservices.tokens.TokenService;
import nodrify.inc.octane.iam.domain.model.aggregates.User;
import nodrify.inc.octane.iam.domain.model.commands.*;
import nodrify.inc.octane.iam.domain.services.UserCommandService;
import nodrify.inc.octane.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import nodrify.inc.octane.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ExternalProfileService externalProfileService;

    /**
     * User Command Service Implementation
     * <p>
     *     This class implements the {@link UserCommandService} interface to handle user-related commands such as {@link SignInCommand}, {@link SignUpCommand} and {@link UpdateUserCommand}.
     * </p>
     */
    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, HashingService hashingService, TokenService tokenService, ExternalProfileService externalProfileService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.externalProfileService = externalProfileService;
    }

    /**
     * Handle update user command
     * <p>
     *     This method handles the {@link UpdateUserCommand} command to update an existing user's details.
     * </p>
     * @param updateUserCommand the command containing the updated user details
     * @param userId the ID of the user to be updated
     * @return an optional containing the updated user
     * @throws IllegalArgumentException if the user with the given ID is not found or if the username is already taken by another user
     */

    @Override
    public Optional<User> handle(UpdateUserCommand updateUserCommand, Long userId) {
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        var existingUserWithEmail = userRepository.findByUsername(updateUserCommand.username());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
            throw new IllegalArgumentException("User with username " + updateUserCommand.username() + " already exists");
        }

        var userToUpdate = userOptional.get();

        String encodedPassword = updateUserCommand.password();
        if (encodedPassword != null && !encodedPassword.isBlank()) {
            encodedPassword = hashingService.encode(encodedPassword);
        } else {
            encodedPassword = userToUpdate.getPassword();
        }

        var commandWithEncodedPassword = new UpdateUserCommand(
                updateUserCommand.username(),
                encodedPassword
        );

        try{
            var updatedUser= userRepository.save(userToUpdate.updateUserDetails(commandWithEncodedPassword));
            return Optional.of(updatedUser);
        }catch (Exception e) { return Optional.empty(); }
    }

    /**
     * Handle delete user command
     * <p>
     *     This method handles the {@link DeleteUserCommand} command to delete an existing user.
     * </p>
     * @param deleteUserCommand the command containing the ID of the user to be deleted
     */
    @Override
    public void handle(DeleteUserCommand deleteUserCommand) {
        if (!userRepository.existsById(deleteUserCommand.userId())) {
            throw new IllegalArgumentException("User with ID " + deleteUserCommand.userId() + " not found");
        }

        try{
            userRepository.deleteById(deleteUserCommand.userId());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    /**
     * Handle sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command to authenticate a user and generate a token.
     * </p>
     * @param signInCommand the command containing the user's credentials
     * @return an optional containing a pair of the authenticated user and the generated token
     * @throws IllegalArgumentException if the user with the given username is not found or if the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand signInCommand) {
        var user = userRepository.findByUsername(signInCommand.username());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with user name " + signInCommand.username() + " not found");
        }
        if (!hashingService.matches(signInCommand.password(), user.get().getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command to register a new user.
     * </p>
     * @param signUpCommand the command containing the new user's details
     * @return an optional containing the newly created user
     * @throws IllegalArgumentException if the username is already taken or if any of the specified roles are not found
     */
    @Override
    public Optional<User> handle(SignUpCommand signUpCommand) {
        if (userRepository.existsByUsername(signUpCommand.username())) {
            throw new IllegalArgumentException("User with user name " + signUpCommand.username() + " already exists");
        }
        var roles= signUpCommand.roles().stream().map(
                role->roleRepository.findByName(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found"))
                ).toList();
        var user = new User(signUpCommand.username(), hashingService.encode(signUpCommand.password()), roles);
        userRepository.save(user);

        try {
            var createProfile = externalProfileService.createProfile(signUpCommand.firstName(), signUpCommand.lastName(), signUpCommand.email(), signUpCommand.photoUrl(), user.getId());
        } catch (Exception e) {
            userRepository.deleteById(user.getId());
            throw new IllegalArgumentException("Failed to create user " + signUpCommand.username());
        }
        /*
        if (createProfile == 0L) {
            // Rollback user creation if profile creation fails
            userRepository.deleteById(user.getId());
            throw new IllegalArgumentException("Failed to create profile for user " + signUpCommand.username());
        }*/

        return userRepository.findByUsername(signUpCommand.username());
    }
}
