package SerializedTest;

import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class sTest {
    public static void main(String[] args) {

        RestAssured.baseURI= "https://rahulshettyacademy.com";

        pojjo p = new pojjo();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("http://google.com");

        List<String> type = new ArrayList<>(Arrays.asList("shoe park", "shop"));
        p.setTypes(type);
        location l = new location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        p.setLocation(l);
        String rssi = given().log().all().body(p)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println(rssi);
    }
}
