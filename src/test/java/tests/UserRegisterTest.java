package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import lib.ApiCoreRequests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;

@Epic("Creating User cases")
@Feature("Creating User")
public class UserRegisterTest extends BaseTestCase {
    private ApiCoreRequests apiCoreRequest = new ApiCoreRequests();

    @Test
    @Description("This test successfully create user")
    @DisplayName("Test positive create user")
    public void testCreateUserSuccess(){
        String userEmail = DataGenerator.getRandomEmail();

        Map<String, String> userData = getUserData(
                "username",
                "firstName",
                "lastName",
                userEmail,
                "12345");

        Response creatingUser = apiCoreRequest
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCode(creatingUser,200);
        Assertions.assertResponseWithKey(creatingUser, "id");
    }
    @Test
    @Description("")
    @DisplayName("Test negative. Create user with bad email")
    public void testCreateUserWithBadEmail(){

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","firstName");
        userData.put("lastName","lastName");
        userData.put("email", "testemail.ru");
        userData.put("password","12345");

        Response creatingUser = apiCoreRequest
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        System.out.println(creatingUser.asString());

        Assertions.assertResponseCode(creatingUser,400);
        Assertions.assertResponseErrorMessage(creatingUser, "Invalid email format");
    }
    @ParameterizedTest
    @CsvSource({
            "'', 'firstName', 'lastName', 'testmail@email.com', '12345'",
            "'username', '', 'lastName','email', '12345'",
            "'username', 'firstName', '','email', '12345'",
            "'username', 'firstName', 'lastName','', '12345'",
            "'username', 'firstName', 'lastName','email', ''"
    })
    @Description("")
    @DisplayName("Test negative. Create user without one parameter")
    public void testCreateUserWithOutOneParameter(String username, String firstName, String lastName, String email, String password){

        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        userData.put("email", email);
        userData.put("password", password);

        String emptyParameter = getEmptyParameter(username, firstName, lastName, email);

        Response creatingUser = apiCoreRequest
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCode(creatingUser,400);
        Assertions.assertResponseErrorMessage(creatingUser, "The value of " + emptyParameter + " field is too short");
    }
    @Test
    @Description("")
    @DisplayName("Test negative. Create user with shor name")
    public void testCreateUserWithShortFirstName(){
        String userEmail = DataGenerator.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName","s");
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password","12345");

        Response creatingUser = apiCoreRequest
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCode(creatingUser,400);
        Assertions.assertResponseErrorMessage(creatingUser,"The value of 'firstName' field is too short");
    }
    @Test
    @Description("")
    @DisplayName("Test negative. Create user with very long name")
    public void testCreateUserWithLongFirstName(){
        String userEmail = DataGenerator.getRandomEmail();

        String longName = "longNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelon" +
                "gNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongNamelongName" +
                "longNamelongNamelongNamelongNamelongNamelongNamelongNamelongName";

        Map<String, String> userData = new HashMap<>();
        userData.put("username", "username");
        userData.put("firstName", longName);
        userData.put("lastName","lastName");
        userData.put("email", userEmail);
        userData.put("password","12345");

        Response creatingUser = apiCoreRequest
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCode(creatingUser,400);
        Assertions.assertResponseErrorMessage(creatingUser,"The value of 'firstName' field is too long");
    }

}
