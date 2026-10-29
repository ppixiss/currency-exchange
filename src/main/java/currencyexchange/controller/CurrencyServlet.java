package currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import currencyexchange.dto.CurrencyDto;
import currencyexchange.exceptoin.CurrencyNotFoundException;
import currencyexchange.service.CurrencyService;
import currencyexchange.validation.InputValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    CurrencyService currencyService = CurrencyService.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.length() < 2) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String code = pathInfo.substring(1);

        if (!InputValidator.isCodeValid(code)){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            CurrencyDto currencyDto = currencyService.findByCode(code);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(currencyDto);
            response.getWriter().write(jsonResponse);
        } catch (CurrencyNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }
}
