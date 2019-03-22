package at.fhwn.ma.serverapp.test.service;

import at.fhwn.ma.serverapp.dto.*;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.repository.ClientDataRepository;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import at.fhwn.ma.serverapp.service.ClientService;
import at.fhwn.ma.serverapp.test.Util.ApplicationTest;
import at.fhwn.ma.serverapp.util.ConnectionData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

/**
 * Created by milos on 17/03/2019.
 */

public class ClientServiceTest extends ApplicationTest{

    @InjectMocks
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private ClientDataRepository clientDataRepository;

    @MockBean
    private RestTemplate restTemplate;

    private static Client client;
    private static ClientData clientData;
    private static List<Client> clientList;
    private static List<ClientData> clientDataList;

    @Before
    public void setup(){

        //given
        client = new Client();
        client.setClientAllias("Milos");
        client.setClientIp("localhost");
        client.setClientPort("8888");
        client.setDataCollectionFrequency(3D);
        client.setDataUploadFrequency(7D);
        client.setIsClientAvailable(true);
        client.setServerAllias("ServerApp");
        client.setServerIp("localhost");
        client.setServerPort("8080");

        clientData = new ClientData();
        clientData.setClientId(1L);
        clientData.setCpuUsage(2D);
        clientData.setMemoryUsage(3D);
        clientData.setTimestamp(new Date());

        clientDataList = new ArrayList<>();
        clientDataList.add(clientData);

        client.setClientData(clientDataList);

        clientList = new ArrayList<>();
        clientList.add(client);

    }

    @Test
    public void loadAllTest(){

        //given
        String ALIAS = client.getClientAllias();

        //when ClientRepository is triggered, return a given list of Client objects
        Mockito.when(clientRepository.findAll())
                .thenReturn(clientList);

        List<Client> loadAllResult = clientService.loadAll();

        //assert that ClientService returns a list Client objects with object at given index having matching alias
        assertThat(loadAllResult.get(0).getClientAllias())
                .isEqualTo(ALIAS);

    }

    @Test
    public void getAllDataByIdTest(){

        //given
        Long ID = 5L;
        WorkloadData workloadData = new WorkloadData();
        workloadData.setId(clientData.getId());
        workloadData.setCpuUsage(clientData.getCpuUsage());
        workloadData.setMemoryUsage(clientData.getMemoryUsage());
        workloadData.setTimestamp(clientData.getTimestamp());

        //when ClientRepository is triggered with given data, return a given Client object
        Mockito.when(clientRepository.findOne(ID)).
                thenReturn(client);

        List<WorkloadData> getAllDataByIdResult = clientService.getAllDataById(ID);

        //assert that ClientService returns a WorkloadData with matching CpuUsage
        assertThat(getAllDataByIdResult.get(0).getCpuUsage())
                .isEqualTo(workloadData.getCpuUsage());

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .findOne(ID);


    }

    @Test
    public void createClientDataTest(){

        //given
        WorkloadData givenWorkloadData = new WorkloadData(clientData);

        //when ClientDataRepository is triggered with any ClientData instance, return a given ClientData
        Mockito.when(clientDataRepository.save(any(ClientData.class)))
                .thenReturn(clientData);

        ClientData createClientDataResult = clientService.create(givenWorkloadData);

        //assert that ClientService with given data returns given ClientData
        assertThat(createClientDataResult)
                .isEqualTo(clientData);

        //assert that ClientService with given data returns ClientData list with matching CPU usage
        assertThat(createClientDataResult.getCpuUsage())
                .isEqualTo(clientData.getCpuUsage());

        //verify that the ClientDataRepository method is invoked certain amount of times
        Mockito.verify(clientDataRepository, times(1))
                .save(any(ClientData.class));

    }

    @Test
    public void insertMultipleWorkloadDataTest(){

        //given
        List<ClientData> toBeInsertedClientData = new ArrayList<>();
        toBeInsertedClientData.add(clientData);
        WorkloadDTO workloadDTO = new WorkloadDTO();

        //when ClientRepository is triggered with any List instance, return a given Client Data list
        Mockito.when(clientDataRepository.save(any(List.class)))
                .thenReturn(toBeInsertedClientData);

        List<ClientData> insertMultipleWorkloadDataResult = clientService.insertMultipleWorkloadData(workloadDTO);

        //assert that ClientService with given data returns given ClientData list
        assertThat(insertMultipleWorkloadDataResult)
                .isEqualTo(toBeInsertedClientData);

        //assert that ClientService with given data returns ClientData list with matching CPU usage
        assertThat(insertMultipleWorkloadDataResult.get(0).getCpuUsage())
                .isEqualTo(clientData.getCpuUsage());

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientDataRepository,times(1))
                .save(any(List.class));

        //TODO referencirati clientDataRepo.save(toBeInsertedClientData) i return tu ref, kako bi metod mogao da se mokuje

    }

    @Test
    public void deleteClientById(){

        //TODO promeniti u ClientService da se delete poziva sa ClientRepo umesto sa ClientDataRepo

        //given
        Long ID = 5L;

        //do nothing when ClientService is triggered with given data
        Mockito.doNothing()
                .when(clientRepository).delete(ID);

        clientService.deleteClientById(ID);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .delete(ID);

    }

    @Test
    public void findByIdTest(){

        //given
        Long ID = 5L;

        //when ClientRepository is triggered with given data, return a given list Client
        Mockito.when(clientRepository.findOne(ID)).
                thenReturn(client);

        Client findByIdResult = clientService.findById(ID);

        //assert that ClientRepository with given data returns matching Client
        assertThat(findByIdResult).
                isEqualTo(client);

        //assert that ClientRepository with given data returns Client with matching alias
        assertThat(findByIdResult.getClientAllias())
                .isEqualTo(client.getClientAllias());

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .findOne(ID);

    }

    @Test
    public void existsTest(){

        //given
        Long ID = 5L;
        Boolean EXISTS = true;

        //when ClientRepository checks if client with id ID exists, return given Boolean value
        Mockito.when(clientRepository.exists(ID)).
                thenReturn(EXISTS);

        Boolean existsResult = clientService.exists(ID);

        //assert that ClientRepository with given data returns Boolean with matching value
        assertThat(existsResult)
                .isEqualTo(EXISTS);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .exists(ID);

    }

    @Test
    public void sendEchoTest(){

        //given
        final String SEND_ECHO = "/client/echoResponse/";
        final int ECHO_VAL = 2;
        String clientHost = ConnectionData.getClientHostById(client);
        String echoUrl = clientHost + SEND_ECHO + ECHO_VAL;
        Double RESULT = 4D;
        Boolean TRUE = true;

        //when RestTemplate is triggered with given data, return a given result
        Mockito.when(restTemplate.getForObject(echoUrl, Double.class))
                .thenReturn(RESULT);

        Boolean sendEchoResult = clientService.sendEcho(client);

        //assert that ClientService with given data returns given result
        assertThat(sendEchoResult)
                .isEqualTo(TRUE);

        //verify that the RestTemplate method is invoked certain amount of times
        Mockito.verify(restTemplate, times(1))
                .getForObject(echoUrl, Double.class);

        //TODO RestTemplate is thread safe, make it a class member and use across many threads

    }

    @Test
    public void isClientAvailableTest(){

        //given
        Long ID = client.getClientId();
        Boolean AVAILABILITY = true;
        ClientService spyClientService = spy(clientService);

        //when ClientRepository is triggered with given data, return a given Client
        Mockito.when(clientRepository.findOne(ID))
                .thenReturn(client);

        //when ClientService is triggered with given data, return a given Boolean value
        Mockito.doReturn(AVAILABILITY).when(spyClientService).sendEcho(client);

        Boolean isClientAvailableResult = spyClientService.isClientAvailable(ID);

        //assert that ClientService with given data returns Boolean with matching value
        assertThat(isClientAvailableResult)
                .isEqualTo(AVAILABILITY);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(spyClientService, Mockito.times(1))
                .sendEcho(client);

    }

    @Test
    public void updateAvailabilityStatusTest(){

        //check if test is relevant

        //given
        Long ID = 5L;
        Boolean AVAILABILITY = true;

        //when ClientRepository is triggered with given data, return a given Client
        Mockito.when(clientRepository.findOne(ID))
                .thenReturn(client);

        clientService.updateAvailabilityStatus(ID, AVAILABILITY);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .findOne(ID);
        Mockito.verify(clientRepository, Mockito.times(1))
                .save(client);

    }

    @Test
    public void getClientFrequencySettingsByIdTest(){

        //given
        Long ID = 5L;

        //when ClientRepository is triggered with given data, return a given Client
        Mockito.when(clientRepository.findOne(ID))
                .thenReturn(client);

        FrequencyDTO getClientFrequencySettingsByIdResult = clientService.getClientFrequencySettingsById(ID);

        //assert that ClientService with given data returns Client with matching frequencies
        assertThat(getClientFrequencySettingsByIdResult.getCollectionFrequency())
                .isEqualTo(client.getDataCollectionFrequency());
        assertThat(getClientFrequencySettingsByIdResult.getUploadFrequency())
                .isEqualTo(client.getDataUploadFrequency());

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository, Mockito.times(1))
                .findOne(ID);

    }

    @Test
    public void changeFrequencyByClientIdTest(){

        //given
        Long ID = 5L;
        FrequencyDTO frequencyDTO = new FrequencyDTO(2D, 10D);
        final String CHANGE_FREQUENCIES_BY_CLIENT_ID = "/client/changeFrequencies/";
        String changeFreqUrl = ConnectionData.getClientHostById(client) + CHANGE_FREQUENCIES_BY_CLIENT_ID;
        HttpStatus OK = HttpStatus.OK;
        RestTemplate rt = new RestTemplate();
        RestTemplate spyRestTemplate = spy(rt);
        ResponseEntity ENTITY = new ResponseEntity(HttpStatus.OK);
        ClientService cs = spy(this.clientService);

        //when ClientRepository is triggered with given data, return a given Client
        Mockito.when(clientRepository.findOne(ID))
                .thenReturn(client);

        //when RestTemplate is triggered with given data, return a given HttpStatus
        Mockito.when(restTemplate.postForEntity(changeFreqUrl, frequencyDTO, FrequencyDTO.class))
                .thenReturn(ENTITY);

        HttpStatus changeFrequencyByClientIdResult = clientService.changeFrequencyByClientId(ID, frequencyDTO);

        //assert that ClientService with given data returns given HttpStatus
        assertThat(changeFrequencyByClientIdResult)
                .isEqualTo(OK);

        //verify that the RestTemplate method is invoked certain amount of times
        Mockito.verify(restTemplate, times(1))
                .postForEntity(changeFreqUrl, frequencyDTO, FrequencyDTO.class);

        //TODO RestTemplate is thread safe, make it a class member and use across many threads

    }

    @Test
    public void setConfigurationTest(){

        //given
        Long ID = 5L;
        ClientConfigDTO clientConfigDTO = new ClientConfigDTO("localhost", "8000");
        String clientHost = ConnectionData.getClientHostById(client);
        final String SET_CONFIGURATION = "/setConfiguration";
        String configUrl = clientHost + SET_CONFIGURATION + "/" + clientConfigDTO.getIp() + "/"
                + clientConfigDTO.getPort();
        HttpStatus OK = HttpStatus.OK;

        //when ClientRepository is triggered with given data, return a given Client
        Mockito.when(clientRepository.findOne(ID))
                .thenReturn(client);

        //when RestTemplate is triggered with given data, return a given HttpStatus
        Mockito.when(restTemplate.getForObject(configUrl, HttpStatus.class))
                .thenReturn(OK);

        HttpStatus setConfigurationResult = clientService.setConfigurationById(ID, clientConfigDTO);

        //assert that ClientService with given data returns given HttpStatus
        assertThat(setConfigurationResult)
                .isEqualTo(OK);

        //verify that the RestTemplate method is invoked certain amount of times
        Mockito.verify(restTemplate, times(1))
                .getForObject(configUrl, HttpStatus.class);

        //TODO RestTemplate is thread safe, make it a class member and use across many threads

    }

    @Test
    public void createClientTest(){

        //given
        ClientDto clientDto = new ClientDto();
        client.setClientId(clientList.size()-1L);
        Long ID = client.getClientId();

        //when ClientRepository is triggered with Client instance, return a given Client
        Mockito.when(clientRepository.save(Matchers.any(Client.class)))
                .thenReturn(client);

        Long createClientResult = clientService.createClient(clientDto);

        //assert that ClientService with given data returns given ID value
        assertThat(createClientResult)
                .isEqualTo(ID);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientRepository,times(1))
                .save(any(Client.class));

    }

    @Test
    public void insertWorkloadDataTest(){

        //given
        WorkloadData workloadData = new WorkloadData();

        //when ClientDataRepository is triggered with given data, return a given ClientData
        Mockito.when(clientDataRepository.save(any(ClientData.class)))
                .thenReturn(clientData);

        clientService.insertWorkloadData(workloadData);

        //verify that the ClientRepository method is invoked certain amount of times
        Mockito.verify(clientDataRepository,times(1))
                .save(any(ClientData.class));

    }

}
