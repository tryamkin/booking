package org.booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetBookingIds {

    @Test
    public void getAllBooking() {

        RestAssured.baseURI = "https://booking-api-dev.herokuapp.com";
        given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(not(empty()))
                .body("bookingid", hasItems(1, 2, 3, 4))
                .body("bookingid", everyItem(isA(Integer.class)))
                .body("bookingid", everyItem(not(nullValue())))
                .log().all();
    }
}
