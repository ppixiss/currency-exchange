package currencyexchange.mapper;

import currencyexchange.dto.ExchangeRateDto;
import currencyexchange.entity.ExchangeRate;

public final class ExchangeRateDtoMapper {
    private ExchangeRateDtoMapper() {
    }

    public static ExchangeRateDto map(ExchangeRate exchangeRate) {
        return new ExchangeRateDto(exchangeRate.getId(),
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate());
    }
}
