package currencyexchange.dto;

public record CurrencyDto(int id,
                          String code,
                          String fullName,
                          String sign) {
}
