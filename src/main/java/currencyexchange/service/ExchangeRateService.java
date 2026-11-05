package currencyexchange.service;

import currencyexchange.dao.ExchangeRateDao;
import currencyexchange.dto.ExchangeRateDto;
import currencyexchange.entity.ExchangeRate;
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

    private ExchangeRateService() {
    }
}
