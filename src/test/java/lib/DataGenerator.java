package lib;

import java.text.SimpleDateFormat;

public class DataGenerator {
    public static String getRandomEmail(){
        String randomEmail = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "testEmail" + randomEmail + "@email.com";
    }
}
