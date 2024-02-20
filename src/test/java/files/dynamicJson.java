package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class dynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){

        RestAssured.baseURI = "http://216.10.245.166";

        String resss = given().log().all().headers("Content-type", "application/json").body(payload.addb(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jss = reUsablecode.rawtoJson(resss);
        String iid = jss.get("ID");
        System.out.println(iid);
    }


    @DataProvider(name = "BooksData")
    public Object[][] bookData(){
        return new Object[][] {{"bed", "223633447"}, {"bcd", "223633449"}, {"bfd", "2236334410"}};
    }
}
