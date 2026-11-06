package currencyexchange.exceptoin;

public class ExchangeRateNotFoundException extends RuntimeException {

    public ExchangeRateNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
