/**
 * @date: 2024/1/9 10:23
 * @author: Vulcan.W
 * @Description:
 */
import com.wj.test.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class testWjCrm {
    @Test
    public void testHelloTest() {
        TestService testService = new TestService();
        String hello = testService.helloTest();
        Assertions.assertEquals("hello",hello);
    }

}

