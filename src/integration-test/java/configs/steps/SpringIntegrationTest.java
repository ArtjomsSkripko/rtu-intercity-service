package configs.steps;

import intercity.IntercityServiceApplication;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = IntercityServiceApplication.class, loader = SpringBootContextLoader.class)
public class SpringIntegrationTest {
}

