package currencyexchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import currencyexchange.dto.CurrencyDto;
import currencyexchange.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final CurrencyService currencyService = CurrencyService.getInstance(); //Создаем переменную CS и
    // записываем ссылку на ед. ссылку на экземпляр этого класса

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException { //todo нормально ли просто в сигнатуре указывать, что может ошибку выбросить?
        response.setContentType("application/json;charset=UTF-8");  //Устанавливаем тип и кодировку ответа

        List<CurrencyDto> currencyDtos = currencyService.findAll(); //Вызываем метод findAll у service

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = objectMapper.writeValueAsString(currencyDtos); //Сериализируем список Java-объектов в json и сохраняем в строку
        response.getWriter().write(jsonResponse); //Записываем эту строку в тело http ответа
    }
}

