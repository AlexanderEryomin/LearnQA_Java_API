import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {
    @Test
    public void logRedirect(){
        String url = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;

        for(;;) {
            if(statusCode == 200)break;
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();

            url = response.getHeader("Location");
            System.out.println("\nHeader Location:");
            System.out.println(url);

            statusCode = response.getStatusCode();
            System.out.println("\nStatus code:");
            System.out.println(statusCode);

        }
    }
}
