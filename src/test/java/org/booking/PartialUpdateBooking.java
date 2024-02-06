package org.booking;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PartialUpdateBooking {
    private static final String BASE_URL = "https://booking-api-dev.herokuapp.com";
    private static String TOKEN;

    @BeforeClass
    public static void setup() {
        // Step 1: Obtain authentication token
        TOKEN = obtainAuthToken("admin", "password123");
    }

    @Test
    public void updateBookingWithPartialPayload() {
        String bookingId = "1"; // Replace with the actual booking ID
        given()
                .baseUri(BASE_URL)
                .basePath("/booking/{id}")
                .pathParam("id", bookingId)
                .header("Content-Type", ContentType.JSON.toString())
                .header("Accept", ContentType.JSON.toString())
                .header("Cookie", "token=" + TOKEN)
                .body("{\n" +
                        "    \"firstname\": \"James1\",\n" +
                        "    \"lastname\": \"Brown\"\n" +
                        "}")
                .when()
                .patch()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .log().body()
                .body("firstname", equalTo("James1"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", notNullValue())
                .body("depositpaid", notNullValue())
                .body("bookingdates.checkin", notNullValue())
                .body("bookingdates.checkout", notNullValue())
                .body("additionalneeds", notNullValue());
    }

    private static String obtainAuthToken(String username, String password) {
        // Step 2: Send a POST request to obtain authentication token
        return given()
                .baseUri(BASE_URL)
                .basePath("/auth")
                .header("Content-Type", ContentType.JSON.toString())
                .header("Accept", ContentType.JSON.toString())
                .body("{\n" +
                        " \"username\" : \"" + username + "\",\n" +
                        " \"password\" : \"" + password + "\"\n" +
                        "}")
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .path("token");
    }


}
