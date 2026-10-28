package currencyexchange.mapper;

import currencyexchange.dto.CurrencyDto;
import currencyexchange.entity.Currency;

public final class CurrencyDtoMapper {
    private CurrencyDtoMapper() {
    }

    public static CurrencyDto map(Currency currency){
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign());
    }
}
