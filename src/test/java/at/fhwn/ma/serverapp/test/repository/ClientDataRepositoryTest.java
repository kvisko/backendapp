package at.fhwn.ma.serverapp.test.repository;

import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.repository.ClientDataRepository;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import at.fhwn.ma.serverapp.test.Util.RepositoryTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by milos on 16/03/2019.
 */

public class ClientDataRepositoryTest extends RepositoryTest {


    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientDataRepository clientDataRepository;

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
        Client clientSaved = clientRepository.save(client);

        ClientData clientData = new ClientData();
        clientData.setClientId(clientSaved.getClientId());
        clientData.setCpuUsage(2D);
        clientData.setMemoryUsage(3D);
        clientData.setTimestamp(new Date());

        ClientData clientData2 = new ClientData();
        clientData2.setClientId(clientSaved.getClientId());
        clientData2.setCpuUsage(21D);
        clientData2.setMemoryUsage(31D);
        clientData2.setTimestamp(new Date());

        clientDataRepository.save(clientData);
        clientDataRepository.save(clientData2);

    }

    @After
    public void h2dbCleanup(){

        clientDataRepository.deleteAll();
        clientRepository.deleteAll();

    }

    @Test
    public void insertWorkloadDataTest() {

        //given
        Long ID = clientRepository.findAll().get(0).getClientId();
        int SIZE = clientRepository.findOne(ID).getClientData().size();
        ClientData clientData = new ClientData();
        clientData.setClientId(ID);
        clientData.setCpuUsage(12D);
        clientData.setMemoryUsage(13D);
        clientData.setTimestamp(new Date());
        clientDataRepository.save(clientData);

        Client insertWorkloadDataResult = clientRepository.findOne(ID);
        List<ClientData> clientDataList = insertWorkloadDataResult.getClientData();

        //get the index of the inserted WorkloadData
        int CURRENT_LAST_INDEX = clientDataList.size()-1;

        //assert that ClientDataRepository returns last inserted ClientData with matching CpuUsage
        assertThat(clientDataList.get(CURRENT_LAST_INDEX).getCpuUsage())
                .isEqualTo(clientData.getCpuUsage());

        //assert that ClientDataRepository returns ClientData list with all current data and size
        assertThat(clientDataList.size())
                .isEqualTo(SIZE + 1);

    }

    @Test
    public void getAllDataByIdTest(){

    //given
    Long ID = clientRepository.findAll().get(0).getClientId();
    int INDEX = 1;
    Double CPU_USAGE = 21D;

    Client client = clientRepository.findOne(ID);
    List<ClientData> getAllDataByIdResult = client.getClientData();

    //assert that ClientDataRepository returns non-empty list of objects
    assertThat(getAllDataByIdResult)
            .isNotEmpty();

    //assert that ClientDataRepository returns ClientData object with given CPU_USAGE at given index
    assertThat(getAllDataByIdResult.get(INDEX).getCpuUsage())
            .isEqualTo(CPU_USAGE);

    }

}
