package nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories;

import nodrify.inc.octane.maintenance.domain.model.entities.ItemType;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.ItemTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemTypeRepository extends JpaRepository<ItemType,Long> {
    Boolean existsByName(ItemTypes name);
}
