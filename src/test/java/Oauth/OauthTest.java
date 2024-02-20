package Oauth;

import io.restassured.path.json.JsonPath;
import pojo.api;
import pojo.getCourse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OauthTest {

    public static void main(String[] args) {
        String rees = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type", "client_credentials")
                .formParam("scope", "trust")
                .when().log().all().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        System.out.println(rees);

        JsonPath js = new JsonPath(rees);
        String accT = js.getString("access_token");

        getCourse gc  = given()
                .queryParam("access_token", accT)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(getCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        List<api> apiCourses = gc.getCourses().getApi();
        for (int i =0; i<apiCourses.size(); i++){
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }
        }
    }
}
