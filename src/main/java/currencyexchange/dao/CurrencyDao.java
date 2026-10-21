package currencyexchange.dao;

import currencyexchange.entity.Currency;
import currencyexchange.exceptoin.DaoException;
import currencyexchange.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDao {
    private static final CurrencyDao INSTANCE = new CurrencyDao();
    private static final String FIND_ALL_SQL = "SELECT id, code, full_name, sign FROM currencies";

    private CurrencyDao(){}

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    public List<Currency> findAll() {
        try (var connection = ConnectionManager.open(); //Открываем соединение
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL); // Создаем переменную для управления подготовленным запросом
             var resultSet = preparedStatement.executeQuery()) { //Выполняем запрос и создаем переменную для получения результата его выполнения

            List<Currency> currencies = new ArrayList<>();
            while (resultSet.next()) { //Сначала находимся перед пустой строкой, переходим на следующую и возвращаем булиан
                Currency currency = new Currency(); //Создаем entity валюты

                currency.setId(resultSet.getInt("id")); //RS обращается к столбцу id в тек. строке, записываем id в сущность
                currency.setCode(resultSet.getString("code"));
                currency.setFullName(resultSet.getString("full_name"));
                currency.setSign(resultSet.getString("sign"));
                currencies.add(currency);
            }
            return currencies;
        } catch (SQLException e) { //Перехватывает исключение SQLException
            throw new DaoException(e); //Создаем и выбрасываем собственное исключение
        }
    }

}
