import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckCookieTest {
    @Test
    public void checkCTest(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie" )
                .andReturn();

        //Checking cookie
        assertTrue( response.getCookies().containsKey("HomeWork") );
        //Checking cookie value
        String expectedValueCookie = "hw_value";
        assertEquals(expectedValueCookie, response.getCookie("HomeWork"),"Bad value cookie" );
    }
}
