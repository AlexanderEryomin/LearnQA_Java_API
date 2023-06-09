package lib;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCoreRequests {
    @Step("Make POST-request for creating user")
    public Response makePostRequest(String url, Map<String, String> userData){
        return given()
                .filter(new AllureRestAssured())
                .body(userData)
                .post(url)
                .andReturn();
    }

    @Step("Make GET-request for receiving user data")
    public Response makeGetRequest(String url, String cookies, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid",cookies)
                .when()
                .get(url)
                .andReturn();
    }
    @Step("Make PUT-request for update user data")
    public Response makePUTRequest(String url, Map<String, String> userData, String cookies, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid",cookies)
                .body(userData)
                .when()
                .put(url)
                .andReturn();
    }
    @Step("Make DELETE-request for delete user")
    public Response makeDeleteRequest(String url, String cookies, String token){
        return given()
                .filter(new AllureRestAssured())
                .header(new Header("x-csrf-token", token))
                .cookie("auth_sid", cookies)
                .when()
                .delete(url)
                .andReturn();
    }
}