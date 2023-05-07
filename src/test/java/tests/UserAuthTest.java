package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;
import lib.Assertions;
import java.util.HashMap;
import java.util.Map;

public class UserAuthTest extends BaseTestCase {
    String userCookie;
    String userHeader;
    int userId;

    @Test
    public void userAuth(){
        Map<String,String> param = new HashMap<>();
        param.put("email", "vinkotov@example.com");
        param.put("password", "1234");

        Response response = RestAssured
                .given()
                .body(param)
                .when()
                .post("https://playground.learnqa.ru/api/user/login")
                .andReturn();

        this.userCookie = this.getCookie(response,"auth_sid");
        this.userHeader = this.getHeader(response, "x-csrf-token");
        this.userId = this.getIntParamFromJson(response,"user_id");

        Response checkParam = RestAssured
                .given()
                .queryParam("user_id", this.userId)
                .cookie("auth_sid", this.userCookie)
                .header("x-csrf-token",this.userHeader)
                .when()
                .get("https://playground.learnqa.ru/api/user/auth")
                .andReturn();
        Assertions.assertJasonByName(checkParam, "user_id", this.userId);
    }
}
