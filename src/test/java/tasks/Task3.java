package tasks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.testng.annotations.Parameters;

import static org.hamcrest.Matchers.notNullValue;

public class Task3 {

    //    Завдання 3
//    Використовуючи бібліотеку REST Assured, напишіть код для виконання GET-запиту до ендпоінту https://booking-api-dev.herokuapp.com/booking із можливістю використання опціональних параметрів запиту для фільтрації результатів.
//    Дополнительные требования: .
//    Реалізуйте три варіанти GET-запитів:
//    Простий GET-запит для отримання всіх ідентифікаторів броней.
//    GET-запит з фільтрацією на ім'я (firstname).
//    GET-запит з фільтрацією за датою заїзду (checkin) та датою виїзду (checkout).
    private static final String BASE_URL = "https://booking-api-dev.herokuapp.com";
    private static final String USERNAME = "Jim";
    private static final String checkin = "2011-03-29";
    private static final String checkout = "2021-11-08";

    @Test
    public void getAllBookingIdsTest() {
        Response response = RestAssured.get(BASE_URL + "/booking");
        response.then().statusCode(200)
                .body("bookingid", notNullValue())
                .log().body()
                .extract().response();
    }

    @Test
    public void getBookingsByFirstNameTest() {
        Response response = RestAssured.given()
                .get(BASE_URL + "/booking" + "?firstname=" + USERNAME);
        response
                .then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .log().body()
                .extract().response();
        System.out.println(response.getBody().asString());
    }


    @Test
    public void getBookingsByCheckInCheckOutDates() {
        Response response = RestAssured.given()
                .get(BASE_URL + "/booking?" + "checkin=" + checkin + "&checkout=" + checkout);
        response.then()
                .statusCode(200)
                .body("bookingid", notNullValue())
                .extract().response();
    }
}
