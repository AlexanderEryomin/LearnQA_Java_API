package tests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import lib.BaseTestCase;
import lib.Assertions;
import lib.ApiCoreRequests;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
@Epic("Editing users cases")
@Feature("Editing users")
public class UserEditTest extends BaseTestCase {
    private ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("Editing user data without authorization")
    @DisplayName("Negative test. Editing user data without authorization")
    @Owner(value = "Eryomin A. V.")
    @Severity(value = SeverityLevel.CRITICAL)
    public void editUserDataWithOutAuth(){
        Integer userId = 1;
        Map<String, String> userData = new HashMap<>();
        userData.put("username", "NewUserName");

        Response response = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, userData,"", "");

        Assertions.assertResponseCode(response, 400);
        Assertions.assertResponseErrorMessage(response, "Auth token not supplied");
    }
    @Test
    @Description("Editing another user's data")
    @DisplayName("Negative test. Editing another user's data")
    @Owner(value = "Eryomin A. V.")
    @Severity(value = SeverityLevel.CRITICAL)
    public void editingAnotherUserData(){

        //Creating user
        String userEmail = DataGenerator.getRandomEmail();
        String userPassword = "12345";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","firstName");
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password",userPassword);

        Response createUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        //User authorization
        Map<String, String>  authUserData = new HashMap<>();
        authUserData.put("email", userEmail);
        authUserData.put("password", userPassword);

        Response authUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authUserData);
        String cookies = authUser.getCookie("auth_sid");
        String token = authUser.getHeader("x-csrf-token");

        //Editing data
        String editingKey = "username";
        String editingValue = "newTestName";
        Map<String, String> editUserData = new HashMap<>();
        editUserData.put(editingKey, editingValue);
        Integer userId = 1;

        Response editingUserData = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + userId, editUserData, cookies, token);

        Assertions.assertResponseCode(editingUserData, 200);

        //Check edited user
        Response editedUser = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, cookies, token);

        Assertions.assertNoTEqualValue(editedUser, editingKey, editingValue);
    }
    @Test
    @Description("Editing email user on bad email")
    @DisplayName("Negative test. Editing email user on bad email")
    @Owner(value = "Eryomin A. V.")
    @Severity(value = SeverityLevel.NORMAL)
    public void editEmailUser(){
        String userEmail = DataGenerator.getRandomEmail();
        String userPassword = "12345";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","firstName");
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password",userPassword);

        Response createUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        //User authorization
        Map<String, String>  authUserData = new HashMap<>();
        authUserData.put("email", userEmail);
        authUserData.put("password", userPassword);

        Response authUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authUserData);
        String cookies = authUser.getCookie("auth_sid");
        String token = authUser.getHeader("x-csrf-token");
        Integer idAuthUser = getIntParamFromJson(authUser, "user_id");

        //Editing data
        Map<String, String> editUserData = new HashMap<>();
        editUserData.put("email", "newEmailgmail.com");

        Response editUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + idAuthUser, editUserData, cookies, token);

        Assertions.assertResponseCode(editUser, 400);
        Assertions.assertResponseErrorMessage(editUser, "Invalid email format");
    }
    @Test
    @Description("Editing firstName user on short firstName")
    @DisplayName("Negative test. Editing firstName user on short firstName")
    @Owner(value = "Eryomin A. V.")
    @Severity(value = SeverityLevel.MINOR)
    public void editFirstNameUser(){
        String userEmail = DataGenerator.getRandomEmail();
        String userPassword = "12345";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","firstName");
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password",userPassword);

        Response createUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        //User authorization
        Map<String, String>  authUserData = new HashMap<>();
        authUserData.put("email", userEmail);
        authUserData.put("password", userPassword);

        Response authUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", authUserData);
        String cookies = authUser.getCookie("auth_sid");
        String token = authUser.getHeader("x-csrf-token");
        Integer idAuthUser = getIntParamFromJson(authUser, "user_id");

        //Editing data
        Map<String, String> editUserData = new HashMap<>();
        editUserData.put("firstName", "A");

        Response editUser = apiCoreRequests
                .makePUTRequest("https://playground.learnqa.ru/api/user/" + idAuthUser, editUserData, cookies, token);

        Assertions.assertResponseCode(editUser, 400);
        Assertions.assertResponseErrorMessageFromJson(editUser, "Too short value for field firstName");

    }
}
