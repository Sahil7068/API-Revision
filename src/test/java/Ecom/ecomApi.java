package Ecom;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import pojo.loginRequest;
import pojo.loginResponse;
import pojo.order;
import pojo.subOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ecomApi {
    public static void main(String[] args) {
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        loginRequest lr = new loginRequest();
        lr.setUserEmail("restassuredsahil@yopmail.com");
        lr.setUserPassword("Sahil@123");

        RequestSpecification reqLog = given().relaxedHTTPSValidation().spec(req).body(lr);
        loginResponse lrss = reqLog.when().post("/api/ecom/auth/login").then().extract().response().as(loginResponse.class);
        String token = lrss.getToken();
        System.out.println("The token is " + token);

        String userId = lrss.getUserId();
        System.out.println("The userid is " + userId);
        System.out.println("The message is " + lrss.getMessage());

        //Add product

        System.out.println("--- Add Product ---");

        RequestSpecification reqadd=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).build();
        RequestSpecification readdd = given().log().all().spec(reqadd).param("productName", "laptop")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productDescription", "Addias Originals")
                .param("productFor", "women")
                .multiPart("productImage", new File("C://Users//user//Downloads//Screenshot 2024-02-13 122155.png"));

        String addproduct = readdd.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
        JsonPath js = new JsonPath(addproduct);
        String productId = js.getString("productId");
        System.out.println("The product id is " + productId);
        String msg = js.getString("message");
        System.out.println(msg);


        //Create Order
        System.out.println("--- Create Order ---");

        RequestSpecification creod=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();
        subOrder sb = new subOrder();
        sb.setCountry("India");
        sb.setProductOrderedId(productId);

        List<subOrder> soo = new ArrayList<>();
        soo.add(sb);
        order o = new order();
        o.setOrders(soo);

        RequestSpecification crod = given().log().all().spec(creod).body(o);
        String responsecreate = crod.when().post("/api/ecom/order/create-order").then().log().all().extract().response().asString();
        System.out.println(responsecreate);


        //Delete Product

        System.out.println("--- Delete Product ---");

        RequestSpecification delProd=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization", token).setContentType(ContentType.JSON).build();

        RequestSpecification dp = given().log().all().spec(delProd).pathParam("productId", productId);
        String deleteResponse = dp.when().delete("/api/ecom/product/delete-product/{productId}").then()
                .log().all().extract().response().asString();
        JsonPath jsre = new JsonPath(deleteResponse);
        String msg1 = jsre.getString("message");
        System.out.println(msg1);
        Assert.assertEquals("Product Deleted Successfully", msg1);







    }
}
