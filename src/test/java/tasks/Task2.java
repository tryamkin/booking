package tasks;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Task2 {
    //    Завдання 2
//    Використовуючи отриманий токен (з попереднього завдання), напишіть код для виконання PUT та DELETE запитів до ендпоінту https://booking-api-dev.herokuapp.com/booking.
//    Додаткові вимоги:
//    Виберіть довільний ідентифікатор броні (bookingId).
//    Для запиту PUT передайте оновлені дані броні у форматі JSON.
    private static String TOKEN;
    private static final String BASE_URL = "https://booking-api-dev.herokuapp.com";

    @BeforeClass
    public static void getTokenTest() {
        Task1 task1 = new Task1();
        TOKEN = task1.getTOKEN();
    }

    @Test
    public void putUserTest() {

        String requestBody = "{\n" +
                "\"firstname\" : \"James11111111111111\",\n" +
                "\"lastname\" : \"Brown\",\n" +
                "\"totalprice\" : 111,\n" +
                "\"depositpaid\" : true,\n" +
                "\"bookingdates\" : {\n" +
                "\"checkin\" : \"2018-01-01\",\n" +
                "\"checkout\" : \"2019-01-01\"\n" +
                "},\n" +
                "\"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        given()
                .baseUri(BASE_URL)
                .basePath("/booking/11")
                .header("Content-Type", ContentType.JSON.toString())
                .header("Accept", ContentType.JSON.toString())
                .header("Cookie", "token=" + TOKEN)
                .when()
                .body(requestBody)
                .put()
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void delUserTest() {
        given()
                .baseUri(BASE_URL)
                .basePath("/booking/14")
                .header("Content-Type", ContentType.JSON.toString())
                .header("Cookie", "token=" + TOKEN)
                .when()
                .delete()
                .then()
                .statusCode(201)
                .log().all();

    }
}
