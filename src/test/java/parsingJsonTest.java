import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;


import io.restassured.path.json.JsonPath;

public class ParsingJsonTest {
    @Test
    public void jsonParsing() {
        JsonPath responseJson = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
       //responseJson.prettyPrint();

        String secondMessage = responseJson.get("messages[1].message");
        System.out.println(secondMessage);
    }
}