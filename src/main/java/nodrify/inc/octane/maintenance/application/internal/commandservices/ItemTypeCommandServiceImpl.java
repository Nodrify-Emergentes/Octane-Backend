package nodrify.inc.octane.maintenance.application.internal.commandservices;


import nodrify.inc.octane.maintenance.domain.model.commands.SeedItemTypesCommand;
import nodrify.inc.octane.maintenance.domain.model.entities.ItemType;
import nodrify.inc.octane.maintenance.domain.model.valueobjects.ItemTypes;
import nodrify.inc.octane.maintenance.domain.services.ItemTypeCommandService;
import nodrify.inc.octane.maintenance.infrastructure.persistence.jpa.repositories.ItemTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class ItemTypeCommandServiceImpl implements ItemTypeCommandService {

    private final ItemTypeRepository itemTypeRepository;

    public ItemTypeCommandServiceImpl(ItemTypeRepository itemTypeRepository) {
        this.itemTypeRepository = itemTypeRepository;
    }

    @Override
    public void handle(SeedItemTypesCommand command) {
        Arrays.stream(ItemTypes.values()).forEach(itemType -> {
            if (!itemTypeRepository.existsByName(itemType)) {
                itemTypeRepository.save(new ItemType(ItemTypes.valueOf(itemType.name())));
            }
        });
    }
}
