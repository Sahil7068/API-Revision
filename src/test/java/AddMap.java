
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import static files.reUsablecode.rawtoJson;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static files.payload.addBody;
import static org.hamcrest.MatcherAssert.assertThat;


public class AddMap {
    public static void main(String[] args) {

                //given - all input details
                //when - submit the api  - Resource and http methods
                //then - validate the response


                RestAssured.baseURI = "https://rahulshettyacademy.com";
                String response = given().log().all().queryParam("key", "qaclick123").headers("Content-Type", "application/json")
                        .body(addBody()).when().post("/maps/api/place/add/json")
                        .then().assertThat().statusCode(200).extract().response().asString();

        System.out.println("The response is " + response);

        JsonPath js = new JsonPath(response);//takes string as input and convert it into json
        String ps = js.getString("place_id");
        System.out.println("The id is " + ps);



        //update

        String adres = "70 Summer walk, USA";
        String updateResponse = given()
                .log().all()
                .headers("Content-Type", "application/json")
                .body("{ \"place_id\": \"" + ps + "\", \"address\": \"" + adres + "\", \"key\": \"qaclick123\" }")
                .when()
                .put("/maps/api/place/update/json")
                .then()
                .extract().response().asString();

        System.out.println("Update response: " + updateResponse);


        // get


        String ress = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", ps)
                .when().get("/maps/api/place/get/json").then().assertThat().statusCode(200).extract().response()
                .asString();

        JsonPath js1 = rawtoJson(ress);
        String addd = js1.getString("address");
        System.out.println("The add is " + addd);

        Assert.assertEquals(adres, addd);
            }
        }

