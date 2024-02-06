package tasks;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Task1 {
 /* Завдання 1
    Використовуючи бібліотеку REST Assured, напишіть код для виконання POST-запиту до ендпоінту https://booking-api-dev.herokuapp.com/auth, передаючи JSON-параметри для автентифікації (username: "admin", password: "password123").
    Додаткові вимоги:
    Вкажіть заголовок Content-Type зі значенням application/json.
    Очікується успішна відповідь (HTTP/1.1 200 OK).
    Отримайте токен із відповіді та збережіть його для використання в наступних запитах.
}*/

    private static final String BASE_URL = "https://booking-api-dev.herokuapp.com";
    private static String TOKEN;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    @Test
    public void authUser() {
        RestAssured.baseURI = BASE_URL;
        String token = given()
                .header("Content-Type", ContentType.JSON.toString())
                .body("{\"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\"}")
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
        System.out.println("Token received: " + token);
        TOKEN=token;
    }

    public String getTOKEN (){
        authUser();
        System.out.println(TOKEN + " private static String TOKEN;");
        return TOKEN;
    }
}
