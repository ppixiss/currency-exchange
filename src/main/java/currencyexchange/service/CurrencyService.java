package currencyexchange.service;

import currencyexchange.dao.CurrencyDao;
import currencyexchange.dto.CurrencyDto;
import currencyexchange.entity.Currency;
import currencyexchange.exceptoin.CurrencyNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CurrencyService {
    private static final CurrencyService INSTANCE = new CurrencyService(); //Создание единественного экземпляра
    private final CurrencyDao currencyDao = CurrencyDao.getInstance(); //Записываем в п. ссылку на ед. экземпляр класса CD

    private CurrencyService() {
    }

    public static CurrencyService getInstance() {
        return INSTANCE;
    }

    public List<CurrencyDto> findAll() { //Метод, который вызывает метод из DAO и преобразует, возвращенные Entity в dto
        List<Currency> currencies = currencyDao.findAll();

        List<CurrencyDto> currencyDtos = new ArrayList<>();

        for (Currency currency : currencies) {
            CurrencyDto currencyDto = createDto(currency);
            currencyDtos.add(currencyDto);
        }
        return currencyDtos;
    }

    public CurrencyDto findByCode(String code) throws CurrencyNotFoundException {
        Currency currency = currencyDao.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found: " + code));

        return createDto(currency);
    }

    public CurrencyDto add(String code, String fullName, String sign) {
        Currency currency = currencyDao.add(code, fullName, sign);

        return createDto(currency);
    }

    private CurrencyDto createDto(Currency currency) {
        return new CurrencyDto(
                currency.getId(),
                currency.getCode(),
                currency.getFullName(),
                currency.getSign());
    }
 }
