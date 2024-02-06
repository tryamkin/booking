package org.booking;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class UpdateBooking {
    private String authToken;
    @Before
    public void setUp () {
        RestAssured.baseURI="https://booking-api-dev.herokuapp.com";
        authToken = obtainAuthToken("admin","password123");

    }

    @Test
    public void updateBooking() {
        int bookingID = 1;
        String requestBody = "{\n" +
                "\"firstname\" : \"James\",\n" +
                "\"lastname\" : \"Brown\",\n" +
                "\"totalprice\" : 111,\n" +
                "\"depositpaid\" : true,\n" +
                "\"bookingdates\" : {\n" +
                "\"checkin\" : \"2018-01-01\",\n" +
                "\"checkout\" : \"2019-01-01\"\n" +
                "},\n" +
                "\"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        Response response = RestAssured.given()
                .headers("Content-Type",ContentType.JSON.toString(),"Accept", ContentType.JSON.toString(),
                        "Cookie", "token=" + authToken )
                .body(requestBody)
                .put("/booking/" + bookingID);

        response.then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .time(lessThan(5000L))
                .body("firstname",equalTo("James"))
                .body( "lastname", equalTo( "Brown"))
                .body( "totalprice", equalTo(  111))
                .body("depositpaid" ,equalTo( true))
                .body ( "bookingdates.checkin", equalTo("2018-01-01"))
                .body ( "bookingdates.checkout", equalTo( "2019-01-01"))
                .body("additionalneeds", equalTo( "Breakfast"));
    }



    private String obtainAuthToken(String username, String password) {
        String requestBody = "{\n"+
                " \"username\": \"" + username + "\",\n" +
                " \"password\": \"" + password + "\"\n" + "}";
        System.out.println("requestBody:  " + requestBody );

        Response response = RestAssured.given()
                .headers("Content-Type",ContentType.JSON.toString(),"Accept", ContentType.JSON.toString())
                .body(requestBody)
                .post("/auth");

        return response.then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("token");
    }


    @After
    public void tearDown() {
        RestAssured.baseURI=null;
    }

}
