package at.fhwn.ma.serverapp.test.repository;

import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import at.fhwn.ma.serverapp.test.Util.RepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by milos on 15/03/2019.
 */

public class ClientRepositoryTest extends RepositoryTest{


    @Autowired
    private ClientRepository clientRepository;

    @Before
    public void h2dbSetup(){

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

    @After
    public void h2dbCleanup(){
        clientRepository.deleteAll();
    }

    @Test
    public void loadAllTest(){

        //given
        String ALIAS = "Milos2";

        List<Client> loadAllResult = clientRepository.findAll();

        //assert that IClientRepository returns non-empty list of objects
        assertThat(loadAllResult)
                .isNotEmpty();

        //assert that IClientRepository returns Client with matching allias
        assertThat(loadAllResult.get(1).getClientAllias())
                .isEqualTo(ALIAS);

    }

    @Test
    public void findClientByIdTest(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        String ALIAS = "Milos";

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

        //persist new Client into the database
        Client createClientResult = clientRepository.save(client2);

        Client client = clientRepository.findOne(createClientResult.getClientId());

        //assert that IClientRepository with ID from created Client returns Client with matching collection frequencies
        assertThat(client.getDataCollectionFrequency())
                .isEqualTo(client2.getDataCollectionFrequency());

    }

    @Test
    public void isClientAvailableTest(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        Boolean AVAILABILITY = true;

        Client client = clientRepository.findOne(ID);
        Boolean isClientAvailableResult = client.getIsClientAvailable();

        //assert that IClientRepository with given data returns Client with matching availability
        assertThat(isClientAvailableResult)
                .isEqualTo(AVAILABILITY);

    }

    @Test
    public void changeFrequencyByClientIdTest(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        Client client = clientRepository.findOne(ID);
        Double CURRENT_COLLECTION_FREQ = client.getDataCollectionFrequency();
        Double CURRENT_UPLOAD_FREQ = client.getDataUploadFrequency();
        Double NEW_COLLECTION_FREQ = 4D;
        Double NEW_UPLOAD_FREQ = 14D;

        //assert that ClientRepository with persisted client's ID returns current given frequencies
        assertThat(client.getDataCollectionFrequency())
                .isEqualTo(CURRENT_COLLECTION_FREQ);
        assertThat(client.getDataUploadFrequency())
                .isEqualTo(CURRENT_UPLOAD_FREQ);

        //set and persist new frequencies
        client.setDataCollectionFrequency(NEW_COLLECTION_FREQ);
        client.setDataUploadFrequency(NEW_UPLOAD_FREQ);
        clientRepository.save(client);

        Client clientWithUpdatedFrequencies = clientRepository.findOne(ID);

        //assert that ClientRepository with persisted client's ID returns updated given frequencies
        assertThat(clientWithUpdatedFrequencies.getDataCollectionFrequency())
                .isEqualTo(NEW_COLLECTION_FREQ);
        assertThat(clientWithUpdatedFrequencies.getDataUploadFrequency())
                .isEqualTo(NEW_UPLOAD_FREQ);

    }

    @Test
    public void getClientFrequencySettingsByIdTest(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        Client client = clientRepository.findOne(ID);
        Double CURRENT_COLLECTION_FREQ = 3D;
        Double CURRENT_UPLOAD_FREQ = 7D;

        Client clientResult = clientRepository.findOne(ID);

        //assert that ClientRepository with given persisted client's ID returns matching frequencies
        assertThat(clientResult.getDataCollectionFrequency())
                .isEqualTo(CURRENT_COLLECTION_FREQ);
        assertThat(clientResult.getDataUploadFrequency())
                .isEqualTo(CURRENT_UPLOAD_FREQ);

    }

    @Test
    public void setConfiguration(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        String CURRENT_IP = "localhost";
        String CURRENT_PORT = "8888";
        String NEW_IP = "newId";
        String NEW_PORT = "newPort";

        Client clientResult = clientRepository.findOne(ID);

        //assert that ClientRepository with persisted client's ID returns current given frequencies
        assertThat(clientResult.getClientIp()).
                isEqualTo(CURRENT_IP);
        assertThat(clientResult.getClientPort()).
                isEqualTo(CURRENT_PORT);

        //set and persist new configuration
        clientResult.setClientIp(NEW_IP);
        clientResult.setClientPort(NEW_PORT);
        clientRepository.save(clientResult);

        Client setConfiguraionResult = clientRepository.findOne(clientResult.getClientId());

        //assert that ClientRepository with persisted client's ID returns updated given configuration
        assertThat(setConfiguraionResult.getClientIp())
                .isEqualTo(NEW_IP);
        assertThat(setConfiguraionResult.getClientPort())
                .isEqualTo(NEW_PORT);

    }

    @Test
    public void deleteTest(){

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();

        Client client = clientRepository.findOne(ID);

        //assert that IClientRepository with given data returns existing Client
        assertThat(client)
                .isNotNull();

        //delete Client from the database
        clientRepository.delete(client.getClientId());

        Client deleteResult = clientRepository.findOne(ID);

        //assert that IClientRepository after deletion does not contain Client object with given ID
        assertThat(deleteResult)
                .isNull();

    }



}
