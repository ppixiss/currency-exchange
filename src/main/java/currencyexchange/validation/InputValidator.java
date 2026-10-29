package currencyexchange.validation;

public final class InputValidator {
    public static final int CODE_LENGTH = 3;

    private InputValidator() {
    }

    public static boolean isCodeValid(String code){
        if (!isCodeLengthValid(code)){
            return false;
        }

        if (code.contains(" ")) {
            return false;
        }

        return !hasDigit(code);
    }

    private static boolean isCodeLengthValid(String code) {
        return code.length() == CODE_LENGTH;
    }

    private static boolean hasDigit(String code) {
        boolean hasDigit = false;
        for (char c: code.toCharArray()) {
            if (Character.isDigit(c)){
                hasDigit = true;
                break;
            }
        }
        return hasDigit;
    }
}
