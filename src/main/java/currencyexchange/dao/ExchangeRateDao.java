package currencyexchange.dao;

import currencyexchange.entity.Currency;
import currencyexchange.entity.ExchangeRate;
import currencyexchange.exceptoin.DaoException;
import currencyexchange.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDao {
    private static final ExchangeRateDao INSTANCE = new ExchangeRateDao();

        private static final String FIND_ALL_SQL = """
                SELECT
                    er.id,
                    er.rate,
                    base.id AS base_id,
                    base.code AS base_code,
                    base.full_name AS base_full_name,
                    base.sign AS base_sign,
                    target.id AS target_id,
                    target.code AS target_code,
                    target.full_name AS target_full_name,
                    target.sign AS target_sign
                 FROM exchange_rates er
                 JOIN currencies base on er.base_currency_id = base.id
                 JOIN currencies target on er.target_currency_id = target.id
                """;

    public static ExchangeRateDao getInstance() {
        return INSTANCE;
    }

    public List<ExchangeRate> findAll() {
        try (var connection = ConnectionManager.open();
             var pStatement = connection.prepareStatement(FIND_ALL_SQL);
             var resultSet = pStatement.executeQuery()) {

            List<ExchangeRate> rates = new ArrayList<>();

            while (resultSet.next()) {
                rates.add(createExchangeRate(resultSet));
            }
            return rates;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private ExchangeRateDao() {
    }

    private ExchangeRate createExchangeRate(ResultSet resultSet) throws SQLException {
        var baseCurrency = new Currency(
                resultSet.getInt("base_id"),
                resultSet.getString("base_code"),
                resultSet.getString("base_full_name"),
                resultSet.getString("base_sign"));

        var targetCurrency = new Currency(
                resultSet.getInt("target_id"),
                resultSet.getString("target_code"),
                resultSet.getString("target_full_name"),
                resultSet.getString("target_sign"));

        return new ExchangeRate(resultSet.getInt("id"),
                baseCurrency,
                targetCurrency,
                resultSet.getBigDecimal("rate"));
    }
}
