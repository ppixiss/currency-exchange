package currencyexchange.dto;

import currencyexchange.entity.Currency;

import java.math.BigDecimal;

public record ExchangeRateDto(int id,
                              Currency baseCurrency,
                              Currency targetCurrency,
                              BigDecimal rate) {
}
