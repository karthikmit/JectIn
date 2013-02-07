package test;

import com.jectin.controller.UsersController;
import org.junit.Test;

import java.io.IOException;

public class SignupAndActivationTests {

    @Test
    public void signupTest() throws IOException {
        UsersController controller = new UsersController();

        String response = controller.signup("test@test.com", "test", "testy");
        System.out.println(response);
    }
}
