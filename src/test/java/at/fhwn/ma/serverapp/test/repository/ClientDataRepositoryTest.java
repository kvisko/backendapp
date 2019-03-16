package at.fhwn.ma.serverapp.test.repository;

import at.fhwn.ma.serverapp.ServerApplication;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.repository.ClientDataRepository;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by milos on 16/03/2019.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ServerApplication.class
)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientDataRepositoryTest {

    @Autowired
    private ClientDataRepository clientDataRepository;

    @Autowired
    private ClientRepository clientRepository;

    Client client = new Client();
    Client clientSaved = null;

    @Before
    public void setup(){

        //given
        client.setClientAllias("Milos");
        client.setClientIp("localhost");
        client.setClientPort("8888");
        client.setDataCollectionFrequency(3D);
        client.setDataUploadFrequency(7D);
        client.setIsClientAvailable(true);
        client.setServerAllias("ServerApp");
        client.setServerIp("localhost");
        client.setServerPort("8080");
        clientSaved = clientRepository.save(client);

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

    @Test
    public void insertWorkloadDataTest() {

        //given
        ClientData clientData = new ClientData();
        clientData.setClientId(clientSaved.getClientId());
        clientData.setCpuUsage(12D);
        clientData.setMemoryUsage(13D);
        clientData.setTimestamp(new Date());
        clientDataRepository.save(clientData);

        Client insertWorkloadDataResilt = clientRepository.findOne(clientData.getClientId());
        List<ClientData> clientDataList = insertWorkloadDataResilt.getClientData();

        //get the index of the inserted WorkloadData
        int CURRENT_INDEX = clientDataList.size()-1;

        //assert that IClientDataRepository returns ClientData with matching CpuUsage
        assertThat(clientDataList.get(CURRENT_INDEX).getCpuUsage())
                .isEqualTo(clientData.getCpuUsage());

    }

    @Test
    public void getAllDataByIdTest(){

    //given
    int INDEX = 1;
    Double CPU_USAGE = 21D;

    Client client = clientRepository.findOne(clientSaved.getClientId());
    List<ClientData> getAllDataByIdResult = client.getClientData();

    //assert that IClientDataRepository returns non-empty list of objects
    assertThat(getAllDataByIdResult)
            .isNotEmpty();

    //assert that IClientDataRepository returns ClientData object with given CPU_USAGE at given index
    assertThat(getAllDataByIdResult.get(INDEX).getCpuUsage())
            .isEqualTo(CPU_USAGE);

    //assert that IClientDataRepository returns ClientData list with all current data and size
    assertThat(getAllDataByIdResult.size())
            .isEqualTo(2);

    }

}
