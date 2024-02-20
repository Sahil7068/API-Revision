package jiraTest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.given;

public class jira1 {
    public static void main(String[] args) {

        RestAssured.baseURI= "http://localhost:8080/";

        SessionFilter s = new SessionFilter();

        String rss = given().headers("Content-Type", "Application/json").body("{\n" +
                "    \"username\": \"sr12001317068\",\n" +
                "    \"password\": \"Sahil@7068\"\n" +
                "}").log().all().filter(s).when().post("rest/auth/1/session").then().extract().response().asString();

        given().pathParam("key", "10101").log().all().headers("Content-Type", "application/json").body("{\n" +
                "    \"username\": \"sr12001317068\",\n" +
                "    \"password\": \"Sahil@7068\"\n" +
                "}").filter(s).when().post("rest/auth/{key}/session");
    }

}
