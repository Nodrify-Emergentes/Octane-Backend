package nrg.inc.bykerz.iam.interfaces.rest;

import nrg.inc.bykerz.assignments.domain.model.aggregates.Mechanic;
import nrg.inc.bykerz.assignments.infrastructure.persistence.jpa.repositories.MechanicRepository;
import nrg.inc.bykerz.iam.domain.model.commands.DeleteUserCommand;
import nrg.inc.bykerz.iam.domain.model.commands.UpdateUserCommand;
import nrg.inc.bykerz.iam.domain.model.queries.*;
import nrg.inc.bykerz.iam.domain.services.UserCommandService;
import nrg.inc.bykerz.iam.domain.services.UserQueryService;
import nrg.inc.bykerz.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import nrg.inc.bykerz.iam.interfaces.rest.resources.MechanicIdResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.OwnerIdResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.UpdateUserResource;
import nrg.inc.bykerz.iam.interfaces.rest.resources.UserResource;
import nrg.inc.bykerz.iam.interfaces.rest.transform.UpdateUserCommandFromResourceAssembler;
import nrg.inc.bykerz.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.bykerz.shared.application.internal.outboundservices.acl.ExternalProfileService;
import nrg.inc.bykerz.vehicles.infrastructure.persistence.jpa.repositories.OwnerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Operations related to users")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final OwnerRepository ownerRepository;
    private final MechanicRepository mechanicRepository;
    private final ExternalProfileService externalProfileService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService, OwnerRepository ownerRepository, MechanicRepository mechanicRepository, ExternalProfileService externalProfileService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.ownerRepository = ownerRepository;
        this.mechanicRepository = mechanicRepository;
        this.externalProfileService = externalProfileService;
    }

    private Long getUserIdFromContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            System.out.println("🪪 User ID from context: " + userDetails.getId());
            return userDetails.getId();
        }
        throw new RuntimeException("Invalid principal type");
    }


    @PutMapping
    @Operation(summary = "Update a user", description = "Update a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "user updated successfully"),
            @ApiResponse (responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<UserResource> updateUser(@RequestBody UpdateUserResource updateUserResource){

        Long userId = getUserIdFromContext();

        //Convertir el recurso a comando
        UpdateUserCommand updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommandFromResource(updateUserResource);

        // Ejecutar el comando
        var userOptional = userCommandService.handle(updateUserCommand,userId);

        // Verificar si el estudiante fue actualizado exitosamente
        if (userOptional.isPresent()) {
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
            return ResponseEntity.ok(userResource); // 200 OK
        } else {
            return ResponseEntity.badRequest().build();
        }

    }




    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user", description = "Deletes a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "204", description = "User deleted successfully"),
            @ApiResponse (responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> deleteUser(@PathVariable Long userId) {

        // Crear el comando de eliminación (No se necesita un recurso para eliminar)
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(userId);

        // Ejecutar el comando de eliminación
        userCommandService.handle(deleteUserCommand);

        // Verificar si el estudiante fue eliminado exitosamente

        return ResponseEntity.noContent().build(); // 204 No Content
    }



    @GetMapping("/{userId}")
    @Operation(summary = "Get a user by ID", description = "Retrieves a user by its ID.")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "user retrieved successfully"),
            @ApiResponse (responseCode = "404", description = "user not found")
    })
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        //Crear el query para obtener el estudiante por ID
        GetUserByIdQuery getUserByIdQuery = new GetUserByIdQuery(userId);

        // Ejecutar el query
        var userOptional = userQueryService.handle(getUserByIdQuery);

        // Verificar si el estudiante fue encontrado
        if (userOptional.isPresent()) {
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
            return ResponseEntity.ok(userResource); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users.")
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse (responseCode = "404", description = "No users found")
    })
    public ResponseEntity<List<UserResource>> getAllUsers() {

        // Crear el query para obtener todos los estudiantes
        GetAllUsersQuery getAllUsersQuery = new GetAllUsersQuery();

        // Ejecutar el query para obtener todos los estudiantes
        var users = userQueryService.handle(getAllUsersQuery); // null para obtener todos

        // Verificar si se encontraron estudiantes
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        // Convertir la lista de estudiantes a recursos
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(userResources); // 200 OK
    }


    @GetMapping("/username/{username}")
    @Operation(summary = "Get a user by username", description = "Retrieves a user by username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getUserByUsername(@PathVariable String username) {
        //Create the query to get the user by userName
        GetUserByUsernameQuery getUserByUsernameQuery =new GetUserByUsernameQuery(username);

        // Execute the query
        var userOptional =userQueryService.handle(getUserByUsernameQuery);

        // Check if the user was found
        if (userOptional.isPresent()) {
            var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
            return ResponseEntity.ok(userResource); // 200 OK
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }


    @GetMapping("/me")
    @Operation(summary = "Get current authenticated user", description = "Retrieves the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> getCurrentUser() {
        try {
            Long userId = getUserIdFromContext(); // obtiene el ID del usuario autenticado
            var userOptional = userQueryService.handle(new GetUserByIdQuery(userId));

            if (userOptional.isPresent()) {
                var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userOptional.get());
                return ResponseEntity.ok(userResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build(); // Si no hay usuario autenticado
        }
    }

    @GetMapping("/owner")
    @Operation(summary = "Get Owner ID of an user", description =  "Retrieves the Owner ID associated with a user")
    public ResponseEntity<OwnerIdResource> getOwnerIdOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var user = userQueryService.handle(getUserByUsernameQuery);
        if (user.isEmpty()) {return ResponseEntity.notFound().build();}
        var userId = user.get().getId();

        var profileId = externalProfileService.getProfileIdByUserId(userId);
        if (profileId == 0L) {return ResponseEntity.notFound().build();}

        var owner = ownerRepository.findOwnerByProfile_Id(profileId);

        if (owner.isEmpty()) {return ResponseEntity.notFound().build();}

        return ResponseEntity.ok(new OwnerIdResource(owner.get().getId()));
    }

    @GetMapping("/mechanic")
    @Operation(summary = "Get Mechanic ID of an user", description =  "Retrieves the Mechanic ID associated with a user")
    public ResponseEntity<MechanicIdResource> getMechanicIdOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var user = userQueryService.handle(getUserByUsernameQuery);
        if (user.isEmpty()) {return ResponseEntity.notFound().build();}
        var userId = user.get().getId();

        var profileId = externalProfileService.getProfileIdByUserId(userId);
        if (profileId == 0L) {return ResponseEntity.notFound().build();}

        var mechanic = mechanicRepository.getMechanicByProfile_Id(profileId);

        if (mechanic.isEmpty()) {return ResponseEntity.notFound().build();}

        return ResponseEntity.ok(new MechanicIdResource(mechanic.get().getId()));
    }



}
