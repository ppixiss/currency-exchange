package currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import currencyexchange.dto.CurrencyDto;
import currencyexchange.exceptoin.CurrencyAlreadyExistsException;
import currencyexchange.service.CurrencyService;
import currencyexchange.validation.InputValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        List<CurrencyDto> currencyDtos = currencyService.findAll();

        String jsonResponse = objectMapper.writeValueAsString(currencyDtos);
        response.getWriter().write(jsonResponse);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String code = request.getParameter("code");
        String name = request.getParameter("full_name");
        String sign = request.getParameter("sign");

        if (code == null || name == null || sign == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The required field is missing.");
            return;
        }

        code = code.trim().toUpperCase();
        name = name.trim();
        sign = sign.trim();

        try {
            InputValidator.validateCode(code);
            InputValidator.validateFullName(name);
            InputValidator.validateSign(sign);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            return;
        }

        try {
            CurrencyDto currencyDto = currencyService.add(code, name, sign);

            String jsonResponse = objectMapper.writeValueAsString(currencyDto);
            response.getWriter().write(jsonResponse);
        } catch (CurrencyAlreadyExistsException e) {
            response.sendError(HttpServletResponse.SC_CONFLICT, e.getMessage());
        }
    }
}

