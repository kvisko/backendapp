package at.fhwn.ma.serverapp.test.repository;

import at.fhwn.ma.serverapp.ServerApplication;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by milos on 15/03/2019.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ServerApplication.class
)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void setup(){

        //given
        Client client = new Client();
        client.setClientAllias("Milos");
        client.setClientIp("localhost");
        client.setClientPort("8888");
        client.setDataCollectionFrequency(3D);
        client.setDataUploadFrequency(7D);
        client.setIsClientAvailable(true);
        client.setServerAllias("ServerApp");
        client.setServerIp("localhost");
        client.setServerPort("8080");
        clientRepository.save(client);

        Client client1 = new Client();
        client1.setClientAllias("Milos2");
        client1.setClientIp("localhost2");
        client1.setClientPort("8989");
        client1.setDataCollectionFrequency(30D);
        client1.setDataUploadFrequency(70d);
        client1.setIsClientAvailable(false);
        client1.setServerAllias("ServerApp");
        client1.setServerIp("localhost");
        client1.setServerPort("8080");
        clientRepository.save(client1);

    }

}
