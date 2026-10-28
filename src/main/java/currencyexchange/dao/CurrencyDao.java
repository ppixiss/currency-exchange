package currencyexchange.dao;

import currencyexchange.entity.Currency;
import currencyexchange.exceptoin.DaoException;
import currencyexchange.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDao {
    private static final CurrencyDao INSTANCE = new CurrencyDao();
    private static final String FIND_ALL_SQL = "SELECT id, code, full_name, sign FROM currencies";
    private static final String FIND_BY_CODE_SQL = FIND_ALL_SQL + """
             WHERE code = ?
            """;
    private static final String ADD_NEW_CURRENCY_SQL = "INSERT INTO currencies (code, full_name, sign)" +
            " VALUES (?, ?, ?)";

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        return INSTANCE;
    }

    public List<Currency> findAll() {
        try (var connection = ConnectionManager.open(); //Открываем соединение
             var pStatement = connection.prepareStatement(FIND_ALL_SQL); // Создаем переменную для управления подготовленным запросом
             var resultSet = pStatement.executeQuery()) { //Выполняем запрос и создаем переменную для получения результата его выполнения

            List<Currency> currencies = new ArrayList<>();

            while (resultSet.next()) {
                currencies.add(createCurrency(resultSet));
            }
            return currencies;
        } catch (SQLException e) { //Перехватывает исключение SQLException
            throw new DaoException(e); //Создаем и выбрасываем собственное исключение
        }
    }

    public Optional<Currency> findByCode(String code) {
        try (var connection = ConnectionManager.open();
             var pStatement = connection.prepareStatement(FIND_BY_CODE_SQL)) {

            pStatement.setString(1, code);
            var resultSet = pStatement.executeQuery();

            Currency currency = null;

            if (resultSet.next()) {
                currency = createCurrency(resultSet);
            }
            return Optional.ofNullable(currency);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Currency add(String code, String fullName, String sign) {
        try (var connection = ConnectionManager.open();
             var pStatement = connection.prepareStatement(ADD_NEW_CURRENCY_SQL, Statement.RETURN_GENERATED_KEYS)) {

            pStatement.setString(1, code);
            pStatement.setString(2, fullName);
            pStatement.setString(3, sign);

            pStatement.executeUpdate();
            var key = pStatement.getGeneratedKeys();

            Currency currency = null;

            if (!key.next()) {
                throw new DaoException("Failed to retrieve generated key");
            }

            int id = key.getInt(1);
            currency = new Currency(id, code, fullName, sign);
            return currency;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Currency createCurrency(ResultSet resultSet) throws SQLException { //todo подумать должен ли этот метод находиться в этом классе?
        return new Currency(resultSet.getInt("id"),
                resultSet.getString("code"),
                resultSet.getString("full_name"),
                resultSet.getString("sign"));
    }
}
