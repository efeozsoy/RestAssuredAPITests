package tests;

import groovy.util.logging.Log;
import helper.Logger;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class RestAssuredTest {

    private final String BASE_URL = "https://reqres.in";

    @Test
    public void singleUserBDDTest() {
        when()
                .get(BASE_URL + "/api/users/2")
                .then().statusCode(200)
                .body("data.email", equalTo("janet.weaver@reqres.in"))
                .time(lessThan(1000L));
        Logger.fine("Email checked successfully!");
    }

    @Test
    public void listSingleResouce() {
        RestAssured.baseURI = "https://reqres.in";
        RequestSpecification httpRequest = given();
        Response response = httpRequest.get("/api/unknown/2");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Logger.info(bodyAsString);
        Assert.assertTrue(bodyAsString.contains("fuchsia rose"), "String did not found");
        Logger.fine("Body checked successfully!");
    }

    @Test
    public void createUserTest() {
        String postData = "{\n" +
                "  \"name\": \"morpheus\",\n" +
                "  \"job\": \"leader\"\n" +
                "}";
        given().
                contentType(ContentType.JSON).
                body(postData).
                when().
                post(BASE_URL + "/api/users").
                then().
                statusCode(201).
                body("name", equalTo("morpheus"));
        Logger.fine("Body name checked successfully!");
    }
}
