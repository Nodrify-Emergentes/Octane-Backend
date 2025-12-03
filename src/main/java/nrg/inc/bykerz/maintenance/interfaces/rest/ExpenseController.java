package nrg.inc.bykerz.maintenance.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.bykerz.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.bykerz.iam.domain.services.UserQueryService;
import nrg.inc.bykerz.maintenance.application.internal.commandservices.ExpenseItemCommandServiceImpl;
import nrg.inc.bykerz.maintenance.domain.model.commands.AddExpenseItemCommand;
import nrg.inc.bykerz.maintenance.domain.model.commands.CreateExpenseByOwnerIdCommand;
import nrg.inc.bykerz.maintenance.domain.model.commands.CreateExpenseCommand;
import nrg.inc.bykerz.maintenance.domain.model.commands.DeleteExpenseCommand;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetAllExpensesByUserIdQuery;
import nrg.inc.bykerz.maintenance.domain.model.queries.GetExpenseByIdQuery;
import nrg.inc.bykerz.maintenance.domain.services.ExpenseCommandService;
import nrg.inc.bykerz.maintenance.domain.services.ExpenseItemCommandService;
import nrg.inc.bykerz.maintenance.domain.services.ExpenseQueryService;
import nrg.inc.bykerz.maintenance.interfaces.rest.resources.CreateExpenseResource;
import nrg.inc.bykerz.maintenance.interfaces.rest.resources.ExpenseResource;
import nrg.inc.bykerz.maintenance.interfaces.rest.transform.ExpenseResourceFromEntityAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/expense")
@Tag(name = "Expense", description = "Expense management API")
public class ExpenseController {
    private final ExpenseCommandService expenseCommandService;
    private final UserQueryService userQueryService;
    private final ExpenseItemCommandService expenseItemCommandService;
    private final ExpenseQueryService expenseQueryService;

    public ExpenseController(ExpenseCommandService expenseCommandService, UserQueryService userQueryService, ExpenseItemCommandService expenseItemCommandService, ExpenseQueryService expenseQueryService) {
        this.expenseCommandService = expenseCommandService;
        this.userQueryService = userQueryService;
        this.expenseItemCommandService = expenseItemCommandService;
        this.expenseQueryService = expenseQueryService;
    }

    @GetMapping
    @Operation(summary = "Get All Expenses By Owner", description = "Get All Expenses of an Owner Token")
    public ResponseEntity<List<ExpenseResource>> getAllExpenses(@AuthenticationPrincipal UserDetails userDetails){
        String username = userDetails.getUsername();
        var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
        var user = userQueryService.handle(getUserByUsernameQuery);
        if (user.isEmpty()) {return ResponseEntity.notFound().build();}
        var userId = user.get().getId();

        var getAllExpensesByUserIdQuery = new GetAllExpensesByUserIdQuery(userId);

        var expenses = expenseQueryService.handle(getAllExpensesByUserIdQuery);

        var expenseResources = expenses.stream()
                .map(ExpenseResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(expenseResources);


    }

    @PostMapping("/owner/{ownerId}")
    @Operation(summary = "Create new Expense By Owner Id", description = "Create a new expense for an owner" )
    public ResponseEntity<ExpenseResource> createExpenseByOwnerId(
            @RequestBody CreateExpenseResource resource,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long ownerId
    ){
        var createExpenseByOwnerIdCommand = new CreateExpenseByOwnerIdCommand(
                resource.name(),
                resource.finalPrice(),
                ownerId,
                resource.expenseType()
        );

        var expense = expenseCommandService.handle(createExpenseByOwnerIdCommand);

        if (expense.isEmpty()) {return ResponseEntity.badRequest().build();}

        resource.items().forEach(item -> {
            try  {
                var addExpenseItemCommand = new AddExpenseItemCommand(
                        expense.get().getId(),
                        item.name(),
                        item.amount(),
                        item.unitPrice(),
                        item.totalPrice(),
                        item.itemType()
                );

                var expenseItem = expenseItemCommandService.handle(addExpenseItemCommand);

            } catch (Exception e) {
                throw new IllegalArgumentException("Error while creating expense item: " + e);
            }
        });

        var createdExpense = expenseQueryService.handle(new GetExpenseByIdQuery(expense.get().getId()));

        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(createdExpense.get());

        return ResponseEntity.ok(expenseResource);
    }


    @PostMapping("/{userId}")
    @Operation(summary = "Create a new expense", description = "Create a new expense for a user")
    public ResponseEntity<ExpenseResource> createExpense(
            @RequestBody CreateExpenseResource resource,
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long userId
            ) {

        var createExpenseCommand = new CreateExpenseCommand(
                resource.name(),
                resource.finalPrice(),
                userId,
                resource.expenseType()
        );

        var expense = expenseCommandService.handle(createExpenseCommand);

        if (expense.isEmpty()) {return ResponseEntity.badRequest().build();}

        resource.items().forEach(item -> {
            try  {
                var addExpenseItemCommand = new AddExpenseItemCommand(
                        expense.get().getId(),
                        item.name(),
                        item.amount(),
                        item.unitPrice(),
                        item.totalPrice(),
                        item.itemType()
                );

                var expenseItem = expenseItemCommandService.handle(addExpenseItemCommand);

            } catch (Exception e) {
                throw new IllegalArgumentException("Error while creating expense item: " + e);
            }
        });

        var createdExpense = expenseQueryService.handle(new GetExpenseByIdQuery(expense.get().getId()));

        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(createdExpense.get());

        return ResponseEntity.ok(expenseResource);
    }

    @GetMapping("/{expenseId}")
    @Operation(summary = "Get an Expense", description = "Get an expense of a user")
    public ResponseEntity<ExpenseResource> getExpense(@PathVariable Long expenseId){
        var expense = expenseQueryService.handle(new GetExpenseByIdQuery(expenseId));
        if (expense.isEmpty()) {return ResponseEntity.notFound().build();}

        var expenseResource = ExpenseResourceFromEntityAssembler.toResourceFromEntity(expense.get());

        return ResponseEntity.ok(expenseResource);
    }

    @DeleteMapping("/{expenseId}")
    @Operation(summary = "Delete an Expense", description = "Delete an expense of a user")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId){
        var deleteExpenseCommand = new DeleteExpenseCommand(expenseId);
        expenseCommandService.handle(deleteExpenseCommand);

        return ResponseEntity.noContent().build();
    }


}
