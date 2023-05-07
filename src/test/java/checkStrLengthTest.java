import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckStrLengthTest {
    @ParameterizedTest
    @ValueSource(strings = {"www","asdasdasdasdqweqw", "asdasdasdasdqweqwqweqwe"})
    public void checkStrLength(String str){
        System.out.println(str.length());
        assertTrue(str.length() > 15, "String is short :(");
    }
}
