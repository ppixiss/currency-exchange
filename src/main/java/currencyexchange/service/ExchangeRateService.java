package currencyexchange.service;

import currencyexchange.dao.ExchangeRateDao;
import currencyexchange.dto.ExchangeRateDto;
import currencyexchange.entity.ExchangeRate;
import currencyexchange.exceptoin.ExchangeRateNotFoundException;
import currencyexchange.mapper.CurrencyDtoMapper;
import currencyexchange.mapper.ExchangeRateDtoMapper;

import java.util.ArrayList;
import java.util.List;

public class ExchangeRateService {
    private static final ExchangeRateService INSTANCE = new ExchangeRateService();
    private final ExchangeRateDao exchangeRateDao = ExchangeRateDao.getInstance();

    public static ExchangeRateService getInstance() {
        return INSTANCE;
    }

    public List<ExchangeRateDto> findAll() {
        List<ExchangeRate> rates = exchangeRateDao.findAll();

        List<ExchangeRateDto> rateDtos = new ArrayList<>();

        for (ExchangeRate rate : rates) {
            rateDtos.add(ExchangeRateDtoMapper.map(rate));
        }
        return rateDtos;
    }

    public ExchangeRateDto findByPairCodes(String baseCurrencyCode, String targetCurrencyCode) {
        ExchangeRate exchangeRate = exchangeRateDao.findByPairCodes(baseCurrencyCode, targetCurrencyCode)
                .orElseThrow(() -> new ExchangeRateNotFoundException("Exchange rate not found: " + baseCurrencyCode + targetCurrencyCode));
        return ExchangeRateDtoMapper.map(exchangeRate);
    }

    private ExchangeRateService() {
    }
}
