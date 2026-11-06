package currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import currencyexchange.dto.ExchangeRateDto;
import currencyexchange.service.ExchangeRateService;
import currencyexchange.validation.InputValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangeRateService exchangeRateService = ExchangeRateService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int CURRENCY_PAIR_PATH_LENGTH = 7;
    private static final int FIRST_CODE_START_INDEX = 1;
    private static final int SECOND_CODE_START_INDEX = 4;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.length() != CURRENCY_PAIR_PATH_LENGTH) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The currency pair codes are missing from the address.");
            return;
        }

        String baseCurrencyCode = pathInfo.substring(FIRST_CODE_START_INDEX, SECOND_CODE_START_INDEX);
        String targetCurrencyCode = pathInfo.substring(SECOND_CODE_START_INDEX);

        try {
            InputValidator.validateCode(baseCurrencyCode);
            InputValidator.validateCode(targetCurrencyCode);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        ExchangeRateDto exchangeRateDto = exchangeRateService.findByPairCodes(baseCurrencyCode, targetCurrencyCode);

        String jsonResponse = objectMapper.writeValueAsString(exchangeRateDto);
        response.getWriter().write(jsonResponse);
    }
}
