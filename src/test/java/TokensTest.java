import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TokensTest {
    @Test
    public void createTicket() throws InterruptedException {

        //Step 1. Create job
        JsonPath createJob = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String token = createJob.get("token");
        int runTime = createJob.get("seconds");
        System.out.println(token);
        System.out.println(runTime);

        //Step 2. Check status job
        Map<String, String> tokenParam = new HashMap<>();
        tokenParam.put("token", token);

        JsonPath checkStatus = RestAssured
                .given()
                .queryParams(tokenParam)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String jobStatus = checkStatus.get("status");
        System.out.println(jobStatus);

        if( jobStatus.equals("Job is NOT ready") ) System.out.println("Correct status");
        else System.out.println("Incorrect status");

        //Step 3. Sleep
        int sleepTime = runTime*1000;
        Thread.sleep(sleepTime);

        //Step 4. Check result
        JsonPath checkResult = RestAssured
                .given()
                .queryParams(tokenParam)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        jobStatus = checkResult.get("status");
        System.out.println(jobStatus);

        String result = checkResult.get("result");
        if( result != null ) {
            System.out.println("\nResult");
            System.out.println(result);
        }
        else System.out.println("Parameter result not found");
    }
}
