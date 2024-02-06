package tasks;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

public class Task4 {

  /*  Завдання 4
    Використовуючи бібліотеку REST Assured, напишіть код для виконання POST-запиту до ендпоінту https://booking-api-dev.herokuapp.com/booking, створюючи нову броню із заданими параметрами.
    Додаткові вимоги:
    Реалізуйте три варіанти POST-запитів за допомогою різних форматів payload (JSON, XML, URLencoded). Очікується успішна відповідь (HTTP/1.1 200 OK).
    ON, XML, URLencoded). Ожидается успешный ответ (HTTP/1.1 200 OK).
    Отримайте ідентифікатор броні з відповіді для подальшого використання.*/
    private final String BASE_URL = "https://booking-api-dev.herokuapp.com";

    @Test
    public void createBookingWithJsonPayload() {
        String requestBody = "{ \"firstname\": \"Ilon\", \"lastname\": \"Maks\", \"totalprice\": 10000, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2024-02-10\", \"checkout\": \"2024-02-15\" }, \"additionalneeds\": \"Breakfast\" }";

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(BASE_URL + "/booking");

        response.then().statusCode(200).extract().response();
        String bookingId = response.jsonPath().getString("bookingid");
        System.out.println("Booking ID: " + bookingId);
    }

    @Test
    public void createBookingWithXmlPayload() {
        RestAssured.port = 443;
        String requestBody =
                "<booking>\n" +
                "    <firstname>John</firstname>\n" +
                "    <lastname>Doe</lastname>\n" +
                "    <totalprice>100</totalprice>\n" +
                "    <depositpaid>true</depositpaid>\n" +
                "    <bookingdates>\n" +
                "        <checkin>2024-02-10</checkin>\n" +
                "        <checkout>2024-02-15</checkout>\n" +
                "    </bookingdates>\n" +
                "    <additionalneeds>Breakfast</additionalneeds>\n" +
                "</booking>";

        Response response = RestAssured.given()
                .header("Content-Type", ContentType.fromContentType("text/xml"))
                //.contentType(ContentType.XML)
                .body(requestBody)
                .post(BASE_URL + "/booking");

        response.then()
                .log().all()
                .statusCode(200)
                .extract().response();
        String bookingId = response.xmlPath().getString("bookingid");
        System.out.println("Booking ID: " + bookingId);
    }

    @Test
    public void createBookingWithUrlEncodedPayload() {
        String urlrequestBody = "firstname=Jim&lastname=Brown&totalprice=111&depositpaid=true&bookingdates%5Bcheckin%5D=2018-01-01&bookingdates%5Bcheckout%5D=2018-01-02";

        Response response = RestAssured.given()
                .header("ContentType","application/x-www-form-urlencoded")
                //.contentType(ContentType.URLENC)
                .body(urlrequestBody)
                .post(BASE_URL + "/booking");

        response.then()
                .log().all()
                //.statusCode(200)
                .extract().response();

    }
}
