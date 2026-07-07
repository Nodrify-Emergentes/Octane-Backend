package nodrify.inc.octane.profiles.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import nodrify.inc.octane.profiles.domain.model.commands.CreateProfileCommand;
import nodrify.inc.octane.profiles.domain.model.valueobjects.EmailAddress;
import nodrify.inc.octane.profiles.domain.model.valueobjects.UserId;
import nodrify.inc.octane.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Getter
    @Column(name = "first_name")
    private String firstName;

    @Getter
    @Column(name = "last_name")
    private String lastName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "email"))})
    private EmailAddress emailAddress;

    @Getter
    @Column(name = "photo_url")
    private String photoUrl;

    @Embedded
    private UserId userId;

    public Profile(String firstName, String lastName, String emailAddress, String photoUrl, Long userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = new EmailAddress(emailAddress);
        this.photoUrl = photoUrl;
        this.userId = new UserId(userId);
    }

    public Profile() {
        super();
        this.emailAddress = new EmailAddress();
    }

    public Profile(UserId userId) {
        this();
        this.userId = userId;
    }

    public Profile(CreateProfileCommand command) {
        this.firstName = command.firstName();
        this.lastName = command.lastName();
        this.emailAddress = new EmailAddress(command.email());
        this.photoUrl = command.photoUrl();
        this.userId = new UserId(command.userId());
    }

    public String getEmailAddress() {
        return emailAddress.email();
    }

    public void updateName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void updateEmail(String email) {
        this.emailAddress = new EmailAddress(email);
    }

    public Long getUserId() { return this.userId.userId(); }

    public String getCompleteName() {
        return this.firstName + " " + this.lastName;
    }
}
