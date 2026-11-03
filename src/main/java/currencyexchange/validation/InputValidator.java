package currencyexchange.validation;

public final class InputValidator {
    private static final int CODE_LENGTH = 3;
    private static final int SIGN_LENGTH = 5;
    private static final int NAME_LENGTH = 50;


    private InputValidator() {
    }

    public static void validateCode(String code) {
        if (code.length() != CODE_LENGTH || hasDigit(code) || code.contains(" ")) {
            throw new IllegalArgumentException("Currency code must consist of exactly 3 Latin letters");
        }
    }

    public static void validateFullName(String name) {
        if (name.length() > NAME_LENGTH || hasDigit(name) || name.isBlank()) {
            throw new IllegalArgumentException("The currency name must not exceed 50 characters, contain digits, or be blank.");
        }
    }

    public static void validateSign(String sign) {
        if (sign.length() > SIGN_LENGTH || hasDigit(sign) || sign.contains(" ")  || sign.isBlank()) {
            throw new IllegalArgumentException("The currency sign must not exceed 5 characters, contain digits, or be blank.");
        }
    }

    private static boolean hasDigit(String string) {
        boolean hasDigit = false;
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        return hasDigit;
    }
    //todo сделать валидацию на диакритические знаки, на латиницу или русские буквы?
}
