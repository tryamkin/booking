package org.booking;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetBooking {

    @BeforeClass
    public static void setUp () {
        RestAssured.baseURI = "https://booking-api-dev.herokuapp.com";
    }


    @Test
    public void testGetBookingById() {
    String booking = "8";
    given()
            .header("Accept","application/json")
            .when()
            .get("/booking/{id}",booking)
            .then()
            .statusCode (200)
            .log().all()
            .body("firstname", not(emptyOrNullString()))
            .body("lastname", not(emptyOrNullString()))
            .body ("totalprice", greaterThan ( 0))
            .body("depositpaid", isA(Boolean.class))
            .body("bookingdates.checkin", not(emptyOrNullString()))
            .body("bookingdates.checkout", not(emptyOrNullString()))
            .body("firstname", equalTo("Sally"))
            .body("lastname", equalTo( "Brown"))
            .body( "totalprice", equalTo( 334))
            .body("depositpaid", equalTo( false))
            .body ("bookingdates.checkin", equalTo(  "2015-02-02"))
            .body("bookingdates.checkout", equalTo("2017-10-18"))
            ;

    }

}
