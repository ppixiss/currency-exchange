package currencyexchange.service;

import currencyexchange.dao.CurrencyDao;
import currencyexchange.dto.CurrencyDto;
import currencyexchange.entity.Currency;
import currencyexchange.exceptoin.CurrencyAlreadyExistsException;
import currencyexchange.exceptoin.CurrencyNotFoundException;
import currencyexchange.mapper.CurrencyDtoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            CurrencyDto currencyDto = CurrencyDtoMapper.map(currency);
            currencyDtos.add(currencyDto);
        }
        return currencyDtos;
    }

    public CurrencyDto findByCode(String code) throws CurrencyNotFoundException {
        Currency currency = currencyDao.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException("Currency not found: " + code));

        return CurrencyDtoMapper.map(currency);
    }

    public CurrencyDto add(String code, String fullName, String sign) {
        if (isCodeNotUnique(code)) {
            throw new CurrencyAlreadyExistsException("Currency already exists: " + code);
        }
        Currency currency = currencyDao.add(code, fullName, sign);

        return CurrencyDtoMapper.map(currency);
    }

    private boolean isCodeNotUnique(String code) {
        Optional<Currency> currency = currencyDao.findByCode(code);

        return currency.isPresent();
    }
 }
