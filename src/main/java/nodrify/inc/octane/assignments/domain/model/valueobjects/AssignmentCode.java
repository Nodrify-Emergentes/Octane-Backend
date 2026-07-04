package nodrify.inc.octane.assignments.domain.model.valueobjects;

import java.security.SecureRandom;

public record AssignmentCode(String code) {
    private static final String ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int LENGTH = 9;

    public AssignmentCode {
        if (code == null || code.length() != LENGTH || !code.matches("[0-9A-Z]{" + LENGTH + "}")) {
            throw new IllegalArgumentException("El código debe tener 9 caracteres alfanuméricos (0-9, A-Z)");
        }
    }

    public static AssignmentCode random() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return new AssignmentCode(sb.toString());
    }
}
