package lib;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class Assertions {
    public static void assertJasonByName(Response response, String name, int expectedValue){
        response.then().assertThat().body("$",hasKey(name));
        int value = response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value doesn't equal expected value");
    }
    public static void assertResponseCode(Response response, int expectedCode){
        assertEquals(expectedCode, response.getStatusCode(), "Code not " + expectedCode);
    }
    public static void assertResponseWithKey(Response response, String expectedKey){
        response.then().assertThat().body("$",hasKey(expectedKey));
    }
    public static void assertResponseWithOutKey(Response response, String expectedKey){
        JsonPath userData = response.jsonPath();
        assertEquals(null,userData.getString(expectedKey));
    }
    public static void assertResponseErrorMessage(Response response, String expectedMessage){
        assertEquals(expectedMessage, response.asString(), "Response message doesn't equal expected message");
    }
    public static void assertResponseErrorMessageFromJson(Response response, String expectedMessage){
        JsonPath jsonPath = response.jsonPath();
        assertEquals(expectedMessage, jsonPath.getString("error"), "Response message doesn't equal expected message");
    }
    public static void assertNoTEqualValue(Response response, String expectedKey, String expectedValue){
        JsonPath jsonPath = response.jsonPath();
        assertNotEquals(expectedValue, jsonPath.getString(expectedKey));
    }
}
