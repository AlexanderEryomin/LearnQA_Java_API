package tests;

import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import lib.ApiCoreRequests;
import lib.DataGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Epic("Delete users")
@Feature("Delete users")
public class UserDeleteTest extends BaseTestCase {
    private ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("Delete user with id 2")
    @DisplayName("Negative test. Delete user with id 2")
    public void deleteUserWithId(){

        //User authorization
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "vinkotov@example.com");
        userData.put("password", "1234");

        Response userAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userData);
        String userCookies = userAuth.getCookie("auth_sid");
        String userToken = userAuth.getHeader("x-csrf-token");

        //Delete user
        Response userDel = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/" + 2, userCookies, userToken);

        Assertions.assertResponseCode(userDel, 400);
        Assertions.assertResponseErrorMessage(userDel, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");

        //Check user with id 2
        Response userCheck = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + 2,"","");
        Assertions.assertResponseWithKey(userCheck, "username");
    }
    @Test
    @Description("Delete user success")
    @DisplayName("Positive test. Delete user success")
    public void deleteUserPositive(){

        //Create user
        String userEmail = DataGenerator.getRandomEmail();
        String userPassword = "12345";
        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","firstName");
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password", userPassword);

        Response userCreate = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",userData);

        Integer userId = getIntParamFromJson(userCreate, "id");

        //User authorization
        Map<String, String> userAuthData = new HashMap<>();
        userAuthData.put("email", userEmail);
        userAuthData.put("password", userPassword);

        Response userAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userAuthData);
        String userCookies = userAuth.getCookie("auth_sid");
        String userToken = userAuth.getHeader("x-csrf-token");


        //Delete user
        Response userDel = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId, userCookies, userToken);

        Assertions.assertResponseCode(userDel, 200);

        //Check deleted user
        Response userCheck = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,"","");

        Assertions.assertResponseCode(userCheck, 404);
    }
    @Test
    @Description("Delete another user")
    @DisplayName("Negative test. Delete another user")
    public void deleteUserNegative(){

        //Create first user
        String firstUserEmail = DataGenerator.getRandomEmail();
        String firstUserPassword = "12345";
        Map<String, String> firstUserData = new HashMap<>();
        firstUserData.put("username", "username");
        firstUserData.put("firstName","firstName");
        firstUserData.put("lastName","lastName");
        firstUserData.put("email", firstUserEmail);
        firstUserData.put("password", firstUserPassword);

        Response userCreateFirst = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",firstUserData);

        Integer userIdFirst = getIntParamFromJson(userCreateFirst, "id");

        //Create second user
        String secondUserEmail = DataGenerator.getRandomEmail();
        String secondUserPassword = "12345";
        Map<String, String> secondUserData = new HashMap<>();
        secondUserData.put("username", "username");
        secondUserData.put("firstName","firstName");
        secondUserData.put("lastName","lastName");
        secondUserData.put("email", secondUserEmail);
        secondUserData.put("password", secondUserPassword);

        Response userCreate = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/",secondUserData);

        //User authorization
        Map<String, String> userAuthData = new HashMap<>();
        userAuthData.put("email", secondUserEmail);
        userAuthData.put("password", secondUserPassword);

        Response secondUserAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userAuthData);
        String userCookies = secondUserAuth.getCookie("auth_sid");
        String userToken = secondUserAuth.getHeader("x-csrf-token");

        //Delete first created user
        Response firstUserDelete = apiCoreRequests
                .makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userIdFirst, userCookies, userToken);

        Assertions.assertResponseCode(firstUserDelete, 200); // why response with status code 200 ???

        //Check first user
        Response userCheck = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userIdFirst,"","");
        userCheck.prettyPrint();
        Assertions.assertResponseCode(userCheck, 200);
    }
}
