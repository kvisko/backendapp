package at.fhwn.ma.serverapp.test.Util;

import at.fhwn.ma.serverapp.ServerApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by milos on 22/03/2019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ServerApplication.class
)
@TestPropertySource(locations = "classpath:application-test.properties")

public class RepositoryTest {
}
