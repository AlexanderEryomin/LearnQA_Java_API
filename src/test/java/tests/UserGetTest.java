package tests;


import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import lib.ApiCoreRequests;
import lib.DataGenerator;
import lib.Assertions;

import java.util.HashMap;
import java.util.Map;


@Epic("Get users data cases")
@Feature("Get user data")
public class UserGetTest extends BaseTestCase {

    private ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("Get data existing user successfully")
    @DisplayName("Test positive. Get data existing user")
    public void getUserData(){
        //Create user
        String testEmail = DataGenerator.getRandomEmail();
        String testPassword = "12345";

        Map<String, String> userDataForCreating = new HashMap<>();
        userDataForCreating.put("username", "username");
        userDataForCreating.put("firstName","firstName");
        userDataForCreating.put("lastName","lastName");
        userDataForCreating.put("email", testEmail);
        userDataForCreating.put("password", testPassword);

        Response createdUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userDataForCreating);

        Integer idCreatedUser = getIntParamFromJson(createdUser, "id");

        //User Auth
        Map<String, String> userDataForAuthorization = new HashMap<>();
        userDataForAuthorization.put("email", testEmail);
        userDataForAuthorization.put("password", testPassword);

        Response authorizedUser = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/login", userDataForAuthorization);


        String cookie = getCookie(authorizedUser, "auth_sid");
        String token = getHeader(authorizedUser, "x-csrf-token");

        //Get data
        Integer idRandomUser = 1;
        Response getUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + idRandomUser, cookie, token);

        Assertions.assertResponseWithKey(getUserData, "username");

        Assertions.assertResponseWithOutKey(getUserData, "id");
        Assertions.assertResponseWithOutKey(getUserData, "email");
        Assertions.assertResponseWithOutKey(getUserData, "firstName");
        Assertions.assertResponseWithOutKey(getUserData, "lastName");

    }

}
