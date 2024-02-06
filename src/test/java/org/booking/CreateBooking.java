package org.booking;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

public class CreateBooking {
    @Test
    public void createBookingTest () {
        RestAssured.baseURI = "https://booking-api-dev.herokuapp.com";
        RestAssured.port = 443;

        String requestBody = "{\n" +
                "\"firstname\" : \"Jim\",\n" +
                "\"lastname\" : \"Brown\",\n" +
                "\"totalprice\" : 111,\n" +
                "\"depositpaid\" : true,\n" +
                "\"bookingdates\" : {\n" +
                "\"checkin\" : \"2018-01-01\",\n" +
                "\"checkout\" : \"2019-01-01\"\n" +
                "},\n" +
                "\"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        String response =
                given()
                        .header("Content-Type", ContentType.JSON)
                        .body(requestBody)
                        .when()
                        .post("/booking")
                        .then()
                        .statusCode(200)
                        .log().body()
                        .body("bookingid", notNullValue())
                        .body("booking.firstname", equalTo("Jim"))
                        .body("booking.lastname", equalTo("Brown"))
                        .body("booking.totalprice", equalTo(111))
                        .body("booking.depositpaid", equalTo(true))
                        .body("booking.bookingdates.checkin", equalTo("2018-01-01"))
                        .body("booking.bookingdates.checkout", equalTo("2019-01-01"))
                        .body("booking.additionalneeds", equalTo("Breakfast"))
                        .extract().asString();
        System.out.println("Response:  " + response);
    }
}
