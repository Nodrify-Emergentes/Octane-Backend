package nodrify.inc.octane.profiles.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.profiles.domain.model.aggregates.Profile;
import nodrify.inc.octane.profiles.domain.model.valueobjects.EmailAddress;
import nodrify.inc.octane.profiles.domain.model.valueobjects.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByEmailAddress(EmailAddress emailAddress);
    boolean existsByEmailAddress(EmailAddress emailAddress);
    Optional<Profile> findByUserId(UserId userId);
}
