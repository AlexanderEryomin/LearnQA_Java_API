package lib;

import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

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
}
