package org.booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateAuthToken {
    private static final String BASE_URL = "https://booking-api-dev.herokuapp.com";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";
    // Тут визначено константи для базового URL API, імені користувача та пароля.
    //  Метод authToken():
    @Test
    public void authToken() {
        //  Цей метод є тестовим методом створення аутентифікаційного токена.
        //  Установка базового URI:
        RestAssured.baseURI = BASE_URL;
        long startTime = System.currentTimeMillis();
        //  Встановлюється базовий URI для RestAssured.
        // Надсилання POST-запиту для отримання токена:
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\"}")
                .when()
                .post("/auth")
                // Тут використовується given() для встановлення параметрів запиту, таких як тип контенту (JSON) та тіло запиту (ім'я користувача та пароль).
                //Метод when() вказує, що зараз буде виконано HTTP-запит POST. .post("/auth") вказує на кінцеву точку API для автентифікації.
                // Перевірка відповіді:
                .then()
                .statusCode(200)
                .body("token", not(isEmptyOrNullString()))
                .body("$", hasKey("token"))
                // Тут перевіряється, що код відповіді дорівнює 200 і у відповіді є не порожній токен. Також перевіряється, що відповідь містить ключ "token".
                // Вилучення токенів:
                .extract()
                .path("token");
                // Метод extract() використовується для отримання значення токена з відповіді.
                // Обчислення часу виконання запиту та виведення результатів:
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Token received: " + token);
        System.out.println("Request execution time: " + executionTime + " milliseconds");
        // Тут вимірюється час виконання запиту та виводяться результати, включаючи отриманий токен та час виконання запиту.
    }
}