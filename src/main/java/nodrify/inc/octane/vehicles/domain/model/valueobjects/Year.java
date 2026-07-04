package nodrify.inc.octane.vehicles.domain.model.valueobjects;

public record Year(String year) {
    private static final String YEAR_PATTERN = "^(19|20)\\d{2}$";

    public Year {
        if (!isValid(year)) {
            throw new IllegalArgumentException(
                    "Invalid year format. Expected format: YYYY (e.g., 1999, 2020)"
            );
        }
    }

    public static boolean isValid(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        return string.matches(YEAR_PATTERN);
    }
}
