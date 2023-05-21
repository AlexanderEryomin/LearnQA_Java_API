package lib;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {
    protected String getHeader(Response Response, String headerName){
        Headers headers = Response.getHeaders();
        assertTrue(headers.hasHeaderWithName(headerName), "Doesn't have header with name: " + headerName);
        return headers.getValue(headerName);
    }
    protected String getCookie(Response Response, String cookieName){
        Map<String, String> cookies = Response.getCookies();
        assertTrue(cookies.containsKey(cookieName),"Doesn't have cookie with name: " + cookieName);
        return cookies.get(cookieName);
    }
    protected Integer getIntParamFromJson(Response Response, String paramName){
        JsonPath param = Response.jsonPath();
        assertTrue(param.getInt(paramName) > 0, "Doesn't have param with name: " + paramName);
        return param.getInt(paramName);
    }
    protected String getStringParamFromJson(Response response, String paramName){
        response.then().assertThat().body("$",hasKey(paramName));
        //assertTrue(param.getInt(paramName) > 0, "Doesn't have param with name: " + paramName);
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getString(paramName);
    }
    protected  String getEmptyParameter(String username, String firstName, String lastName, String email){
        if ( username.equals("") ){
            return  "'username'";
        }else if ( firstName.equals("") ){
            return "'firstName'";
        }else if ( lastName.equals("") ){
            return  "'lastName'";
        }else if ( email.equals("") ){
            return "'email'";
        }
        return "'password'";
    }
    protected Map<String,String> getUserData(String username, String firstName, String lastName, String email, String password){
        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);
        userData.put("email", email);
        userData.put("password", password);

        return  userData;
    }
}
