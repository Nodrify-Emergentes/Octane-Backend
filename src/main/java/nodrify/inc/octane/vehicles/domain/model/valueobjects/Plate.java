package nodrify.inc.octane.vehicles.domain.model.valueobjects;

public record Plate(String plate) {
    private static final String PLATE_PATTERN = "^[0-9]{4}-[A-Z]{2}$";

    public Plate {
        if (!isValid(plate)) {
            throw new IllegalArgumentException(
                    "Invalid plate format. Expected format: 1234-AB"
            );
        }
    }

    public static boolean isValid(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return string.matches(PLATE_PATTERN);
    }
}
