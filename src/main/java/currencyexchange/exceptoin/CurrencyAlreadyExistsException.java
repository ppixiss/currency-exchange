package currencyexchange.exceptoin;

public class CurrencyAlreadyExistsException extends RuntimeException{

    public CurrencyAlreadyExistsException(String message) {
        super(message);
    }

}
