package tasks;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Task5 {

    private final String BASE_URL = "https://booking-api-dev.herokuapp.com";


    private static String AUTH_TOKEN ;
    @BeforeClass
    public static void getTokenTest() {
        Task1 task1 = new Task1();
        AUTH_TOKEN = task1.getTOKEN();
    }
    @Test
    public void updateBookingWithJsonPayload() {
        String id = "1";
        String jsonPayload = "{\n" +
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
                .basePath("/booking/" + id)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Cookie", "token=" + AUTH_TOKEN)
                .when()
                .body(jsonPayload)
                .put()
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void updateBookingWithXmlPayload() {
        String id = "1";
        String xmlPayload = "<booking>\n" +
                "    <firstname>James</firstname>\n" +
                "    <lastname>Brown</lastname>\n" +
                "    <totalprice>111</totalprice>\n" +
                "    <depositpaid>true</depositpaid>\n" +
                "    <bookingdates>\n" +
                "      <checkin>2018-01-01</checkin>\n" +
                "      <checkout>2019-01-01</checkout>\n" +
                "    </bookingdates>\n" +
                "    <additionalneeds>Breakfast</additionalneeds>\n" +
                "  </booking>";

         given()
                 .baseUri(BASE_URL)
                 .basePath("/booking/" + id)
                 .contentType(ContentType.XML)
                 //.header("Content-Type", "application/xml")
                .header("Accept", "application/xml")
                .header("Cookie", "token=" + AUTH_TOKEN)
                .body(xmlPayload)
                .put()
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.XML);

    }

    @Test
    public void updateBookingWithUrlEncodedPayload() {
        String id = "1";
        String urlEncodedPayload = "firstname=Jim&lastname=Brown&totalprice=111&depositpaid=true&bookingdates%5Bcheckin%5D=2018-01-01&bookingdates%5Bcheckout%5D=2018-01-02";
        RestAssured.port = 443;
         given()
              //  .contentType(ContentType.URLENC)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=" + AUTH_TOKEN)
                .body(urlEncodedPayload)
                .put(BASE_URL + "/booking/" + id)
                .then()
                .log().all()
                .statusCode(200)
                 .extract().response();

    }
}

