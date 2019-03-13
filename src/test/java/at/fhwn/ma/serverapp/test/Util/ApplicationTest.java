package at.fhwn.ma.serverapp.test.Util;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by milos on 13/03/2019.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ApplicationTest {

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

}
