package at.fhwn.ma.serverapp.test.repository;

import at.fhwn.ma.serverapp.ServerApplication;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

    //TODO compiler throws DataIntegrityViolationException if ClientAllias has "unique" constraint,
    //although alias of each client persisted in h2 is unique, otherwise tests pass


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

    @Test
    public void loadAllTest(){

        List<Client> loadAllResult = clientRepository.findAll();

        //assert that IClientRepository returns non-empty list of objects
        assertThat(loadAllResult)
                .isNotEmpty();

        //assert that IClientRepository returns Client with matching allias
        assertThat(loadAllResult.get(1).getClientAllias())
                .isEqualTo("Milos2");

        clientRepository.deleteAll();

        List<Client> loadAllResult2 = clientRepository.findAll();

        //assert that IClientRepository returns empty list of objects after deleteAll
        assertThat(loadAllResult2)
                .isEmpty();

    }

    @Test
    public void findClientByIdTest(){

        //given
        Long ID = 2L;
        String ALIAS = "Milos2";

        Client result = clientRepository.findOne(ID);

        //assert that IClientRepository with given data returns Client with matching allias
        assertThat(result.getClientAllias())
                .isEqualTo(ALIAS);

    }

    @Test
    public void createClientTest(){

        //given
        Client client2 = new Client();
        client2.setClientAllias("Milos3");
        client2.setClientIp("localhost3");
        client2.setClientPort("8008");
        client2.setDataCollectionFrequency(13D);
        client2.setDataUploadFrequency(17d);
        client2.setIsClientAvailable(true);
        client2.setServerAllias("ServerApp");
        client2.setServerIp("localhost");
        client2.setServerPort("8080");
        clientRepository.save(client2);

        Client createClientResult = clientRepository.save(client2);

        Client client = clientRepository.findOne(createClientResult.getClientId());

        //assert that IClientRepository with ID from created Client returns Client with matching alliases
        assertThat(client.getDataCollectionFrequency())
                .isEqualTo(client2.getDataCollectionFrequency());

    }

    @Test
    public void isClientAvailableTest(){

        //given
        Long ID = 1L;
        Boolean AVAILABILITY = true;

        Client client = clientRepository.findOne(ID);
        Boolean isClientAvailableResult = client.getIsClientAvailable();

        //assert that IClientRepository with given data returns Client with matching availability
        assertThat(isClientAvailableResult)
                .isEqualTo(AVAILABILITY);

    }

    @Test
    public void changeFrequencyByClientIdTest(){

        //TODO h2 deletes all entries at this point so new Client has to be persisted for testing purposes, find out why

        //given
//        Long ID = 1L;
        Double CURRENT_COLLECTION_FREQ = 3D;
        Double CURRENT_UPLOAD_FREQ = 7D;
        Double COLLECTION_FREQ = 4D;
        Double UPLOAD_FREQ = 14D;

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
        Client clientSaved = clientRepository.save(client);


        Client clientResult = clientRepository.findOne(clientSaved.getClientId());

        //assert that IClientRepository with persisted client's ID returns current given frequencies
        assertThat(clientResult.getDataCollectionFrequency())
                .isEqualTo(CURRENT_COLLECTION_FREQ);
        assertThat(clientResult.getDataUploadFrequency())
                .isEqualTo(CURRENT_UPLOAD_FREQ);

        //set and persist new frequencies
        clientResult.setDataCollectionFrequency(COLLECTION_FREQ);
        clientResult.setDataUploadFrequency(UPLOAD_FREQ);
        clientRepository.save(clientResult);

        Client clientWithUpdatedFrequencies = clientRepository.findOne(clientSaved.getClientId());

        //assert that IClientRepository with persisted client's ID returns updated given frequencies
        assertThat(clientWithUpdatedFrequencies.getDataCollectionFrequency())
                .isEqualTo(COLLECTION_FREQ);
        assertThat(clientWithUpdatedFrequencies.getDataUploadFrequency())
                .isEqualTo(UPLOAD_FREQ);

    }

    @Test
    public void getClientFrequencySettingsByIdTest(){

        //TODO h2 deletes all entries at this point so new Client has to be persisted for testing purposes, find out why

        //given
//        Long ID = 1L;
        Double CURRENT_COLLECTION_FREQ = 3D;
        Double CURRENT_UPLOAD_FREQ = 7D;

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
        Client clientSaved = clientRepository.save(client);

        Client clientResult = clientRepository.findOne(clientSaved.getClientId());

        //assert that IClientRepository with given persisted client's ID returns matching frequencies
        assertThat(clientResult.getDataCollectionFrequency())
                .isEqualTo(CURRENT_COLLECTION_FREQ);
        assertThat(clientResult.getDataUploadFrequency())
                .isEqualTo(CURRENT_UPLOAD_FREQ);

    }

    @Test
    public void setConfiguration(){

        //TODO h2 deletes all entries at this point so new Client has to be persisted for testing purposes, find out why

        //given
        Long ID = 1L;
        String CURRENT_IP = "localhost";
        String CURRENT_PORT = "8888";
        String IP = "newId";
        String PORT = "newPort";

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
        Client clientSaved = clientRepository.save(client);

        Client clientResult = clientRepository.findOne(clientSaved.getClientId());

        //assert that IClientRepository with persisted client's ID returns current given frequencies
        assertThat(clientResult.getClientIp()).
                isEqualTo(CURRENT_IP);
        assertThat(clientResult.getClientPort()).
                isEqualTo(CURRENT_PORT);

        //set and persist new configuration
        clientResult.setClientIp(IP);
        clientResult.setClientPort(PORT);
        clientRepository.save(clientResult);

        Client setConfiguraionResult = clientRepository.findOne(clientResult.getClientId());

        //assert that IClientRepository with persisted client's ID returns updated given configuration
        assertThat(setConfiguraionResult.getClientIp())
                .isEqualTo(IP);
        assertThat(setConfiguraionResult.getClientPort())
                .isEqualTo(PORT);

    }



}
