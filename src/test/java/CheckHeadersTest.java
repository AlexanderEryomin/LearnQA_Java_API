import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckHeadersTest {
    @Test
    public void checkHeaders(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();

        String secretHeader = "x-secret-homework-header";
        String valueSecretHeader = "Some secret value";
        Headers header = response.getHeaders();
        //Checking header
        assertTrue(header.hasHeaderWithName(secretHeader));
        //Checking header value
        assertEquals(valueSecretHeader, header.getValue(secretHeader),"");
    }
}
