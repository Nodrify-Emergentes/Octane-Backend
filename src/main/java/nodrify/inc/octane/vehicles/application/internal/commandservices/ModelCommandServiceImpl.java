package nodrify.inc.octane.vehicles.application.internal.commandservices;

import nodrify.inc.octane.vehicles.domain.model.commands.CreateModelCommand;
import nodrify.inc.octane.vehicles.domain.model.commands.SeedModelsCommand;
import nodrify.inc.octane.vehicles.domain.model.aggregates.Model;
import nodrify.inc.octane.vehicles.domain.services.ModelCommandService;
import nodrify.inc.octane.vehicles.infrastructure.persistence.jpa.repositories.ModelRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Service
public class ModelCommandServiceImpl implements ModelCommandService {

    final private ModelRepository modelRepository;

    public ModelCommandServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public Optional<Model> handle(CreateModelCommand createModelCommand) {

        Model model = new Model(createModelCommand);

        try{
            modelRepository.save(model);
            return Optional.of(model);
        } catch(Exception e){
            throw new IllegalArgumentException("Error creating model: " + e.getMessage());
        }
    }

    @Override
    public void handle(SeedModelsCommand command) {

        var commands = new CreateModelCommand[]{
                // --- Honda ---
                new CreateModelCommand(
                        "CB190R",
                        "Honda",
                        "2024",
                        "Japón",
                        new Date(),
                        "Naked",
                        "184 cc",
                        "17 hp",
                        "Monocilíndrico 4T",
                        "16 Nm",
                        "140 kg",
                        "5 velocidades",
                        "Disco / Disco",
                        "12 L",
                        "771 mm",
                        "34 km/L",
                        4500.0f,
                        "1.2 L",
                        "No",
                        "Alta",
                        "90"
                ),
                new CreateModelCommand(
                        "CB300 TWISTER",
                        "Honda",
                        "2024",
                        "Brasil",
                        new Date(),
                        "Naked",
                        "293 cc",
                        "24.7 hp",
                        "Monocilíndrico 4T",
                        "27 Nm",
                        "139 kg",
                        "6 velocidades",
                        "Disco / Disco ABS",
                        "14 L",
                        "789 mm",
                        "30 km/L",
                        5200.0f,
                        "1.5 L",
                        "No",
                        "Alta",
                        "90"
                ),
                new CreateModelCommand(
                        "CBR500R",
                        "Honda",
                        "2024",
                        "Japón",
                        new Date(),
                        "Sport",
                        "471 cc",
                        "47 hp",
                        "Bicilíndrico 4T",
                        "43 Nm",
                        "192 kg",
                        "6 velocidades",
                        "ABS delantero y trasero",
                        "17.1 L",
                        "785 mm",
                        "28 km/L",
                        8600.0f,
                        "1.8 L",
                        "Sí",
                        "Alta",
                        "95"
                ),

                // --- Yamaha ---
                new CreateModelCommand(
                        "MT-03",
                        "Yamaha",
                        "2024",
                        "Japón",
                        new Date(),
                        "Naked",
                        "321 cc",
                        "42 hp",
                        "Bicilíndrico 4T",
                        "29.6 Nm",
                        "168 kg",
                        "6 velocidades",
                        "ABS doble disco",
                        "14 L",
                        "780 mm",
                        "27 km/L",
                        7200.0f,
                        "1.8 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "R3",
                        "Yamaha",
                        "2024",
                        "Japón",
                        new Date(),
                        "Sport",
                        "321 cc",
                        "42 hp",
                        "Bicilíndrico 4T",
                        "29.6 Nm",
                        "170 kg",
                        "6 velocidades",
                        "ABS",
                        "14 L",
                        "780 mm",
                        "26 km/L",
                        7300.0f,
                        "1.8 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "FZ25",
                        "Yamaha",
                        "2024",
                        "India",
                        new Date(),
                        "Street",
                        "249 cc",
                        "20.8 hp",
                        "Monocilíndrico 4T",
                        "20 Nm",
                        "153 kg",
                        "5 velocidades",
                        "ABS delantero y trasero",
                        "14 L",
                        "795 mm",
                        "38 km/L",
                        4200.0f,
                        "1.2 L",
                        "No",
                        "Media",
                        "90"
                ),

                // --- Kawasaki ---
                new CreateModelCommand(
                        "Ninja 500",
                        "Kawasaki",
                        "2024",
                        "Japón",
                        new Date(),
                        "Sport",
                        "451 cc",
                        "45 hp",
                        "Bicilíndrico 4T",
                        "42 Nm",
                        "171 kg",
                        "6 velocidades",
                        "ABS doble disco",
                        "14 L",
                        "785 mm",
                        "26 km/L",
                        8600.0f,
                        "1.8 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "Z650",
                        "Kawasaki",
                        "2024",
                        "Japón",
                        new Date(),
                        "Naked",
                        "649 cc",
                        "68 hp",
                        "Bicilíndrico 4T",
                        "64 Nm",
                        "187 kg",
                        "6 velocidades",
                        "ABS doble disco",
                        "15 L",
                        "790 mm",
                        "23 km/L",
                        9600.0f,
                        "2.0 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "Z900",
                        "Kawasaki",
                        "2024",
                        "Japón",
                        new Date(),
                        "Naked",
                        "948 cc",
                        "125 hp",
                        "4 cilindros 4T",
                        "98.6 Nm",
                        "212 kg",
                        "6 velocidades",
                        "ABS doble disco",
                        "17 L",
                        "795 mm",
                        "20 km/L",
                        11500.0f,
                        "3.0 L",
                        "Sí",
                        "Alta",
                        "95"
                ),

                // --- Suzuki ---
                new CreateModelCommand(
                        "GSX125",
                        "Suzuki",
                        "2024",
                        "India",
                        new Date(),
                        "Street",
                        "125 cc",
                        "10.4 hp",
                        "Monocilíndrico 4T",
                        "9.2 Nm",
                        "107 kg",
                        "5 velocidades",
                        "Tambor / Disco",
                        "14.2 L",
                        "780 mm",
                        "50 km/L",
                        2200.0f,
                        "0.9 L",
                        "No",
                        "Media",
                        "90"
                ),
                new CreateModelCommand(
                        "Gixxer SF 150",
                        "Suzuki",
                        "2024",
                        "India",
                        new Date(),
                        "Sport",
                        "155 cc",
                        "14.1 hp",
                        "Monocilíndrico 4T",
                        "14 Nm",
                        "146 kg",
                        "5 velocidades",
                        "ABS delantero",
                        "12 L",
                        "795 mm",
                        "45 km/L",
                        2600.0f,
                        "1.0 L",
                        "No",
                        "Media",
                        "90"
                ),
                new CreateModelCommand(
                        "Gixxer Naked 250",
                        "Suzuki",
                        "2024",
                        "India",
                        new Date(),
                        "Naked",
                        "249 cc",
                        "26 hp",
                        "Monocilíndrico 4T",
                        "22.2 Nm",
                        "156 kg",
                        "6 velocidades",
                        "ABS doble disco",
                        "12 L",
                        "795 mm",
                        "35 km/L",
                        4200.0f,
                        "1.2 L",
                        "No",
                        "Alta",
                        "90"
                ),

                // --- KTM ---
                new CreateModelCommand(
                        "Duke 200",
                        "KTM",
                        "2024",
                        "Austria",
                        new Date(),
                        "Naked",
                        "199.5 cc",
                        "25 hp",
                        "Monocilíndrico 4T",
                        "19 Nm",
                        "140 kg",
                        "6 velocidades",
                        "ABS delantero y trasero",
                        "13.4 L",
                        "810 mm",
                        "35 km/L",
                        4700.0f,
                        "1.2 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "Duke 250",
                        "KTM",
                        "2024",
                        "India",
                        new Date(),
                        "Naked",
                        "249 cc",
                        "30 hp",
                        "Monocilíndrico 4T",
                        "24 Nm",
                        "162 kg",
                        "6 velocidades",
                        "ABS doble canal",
                        "13.5 L",
                        "822 mm",
                        "33 km/L",
                        5300.0f,
                        "1.3 L",
                        "Sí",
                        "Alta",
                        "95"
                ),
                new CreateModelCommand(
                        "Duke 390",
                        "KTM",
                        "2024",
                        "Austria",
                        new Date(),
                        "Naked",
                        "399 cc",
                        "44 hp",
                        "Monocilíndrico 4T",
                        "39 Nm",
                        "168 kg",
                        "6 velocidades",
                        "ABS doble canal",
                        "15 L",
                        "800 mm",
                        "28 km/L",
                        6700.0f,
                        "1.5 L",
                        "Sí",
                        "Alta",
                        "95"
                )
        };

        Arrays.stream(commands).forEach(element -> {
                if (!modelRepository.existsByName(element.name()))
                {
                    modelRepository.save(new Model(element));
                }
        });
    }
}
