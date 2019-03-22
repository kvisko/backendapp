package at.fhwn.ma.serverapp.test.controller;

import at.fhwn.ma.serverapp.controller.ClientController;
import at.fhwn.ma.serverapp.dto.*;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.service.ClientService;
import at.fhwn.ma.serverapp.test.Util.ApplicationTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by milos on 13/03/2019.
 */

public class ClientControllerTest extends ApplicationTest{

    @InjectMocks
    private ClientController clientController;

    @MockBean
    private ClientService clientService;


    @Test
    public void getAllClientsTest(){

        //given
        Client client = new Client();
        client.setClientAllias("Milos");
        List<Client> clients = new ArrayList<>();
        clients.add(client);

        //when ClientService is triggered, return a given list of objects
        Mockito.when(clientService.loadAll())
                .thenReturn(clients);

        ResponseEntity result =  clientController.getAllClients();
        List<Client> clientSearchResult = (List<Client>) result.getBody();

        //assert that ClientController returns non-empty list of objects
        assertThat(clientSearchResult)
                .isNotEmpty();

        //assert that ClientController returns Client with matching allias
        assertThat(clientSearchResult.get(0).getClientAllias())
                .isEqualTo(client.getClientAllias());

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .loadAll();

    }

    @Test
    public void createClientTest(){

        //given
        Long id = 5L;
        ClientDto clientDto = new ClientDto();

        //when ClientService is triggered with given data, return a given id
        Mockito.when(clientService.createClient(clientDto))
                .thenReturn(id);

        ResponseEntity result = clientController.createClient(clientDto);
        Long clientCreateResult = (Long) result.getBody();

        //assert that ClientController with given data returns client with matching id
        assertThat(clientCreateResult)
                .isEqualTo(id);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .createClient(clientDto);

    }

    @Test
    public void addSingleClientDataTest(){

        //TODO proveriti relevantnost testa

        //given
        WorkloadData workloadData = new WorkloadData();
        ResponseEntity status = new ResponseEntity(HttpStatus.OK);

        //do nothing when ClientService is triggered with given data
        Mockito.doNothing()
                .when(clientService).insertWorkloadData(workloadData);

        ResponseEntity addSingleClientDataResult = clientController.addSingleClientData(workloadData);

        //assert that ClientController with given data returns a matching HttpStatus.OK
        assertThat(addSingleClientDataResult)
                .isEqualTo(status);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .insertWorkloadData(workloadData);

    }

    @Test
    public void addClientDataTest(){

        //TODO proveriti relevantnost testa

        //given
        WorkloadDTO workloadDTO = new WorkloadDTO();
        HttpStatus status = HttpStatus.OK;
        List<ClientData> clientData = new ArrayList<>();

        //when ClientService is triggered with given data, return a given list of objects
        Mockito.when(clientService.insertMultipleWorkloadData(workloadDTO))
                .thenReturn(clientData);

        ResponseEntity result = clientController.addClientData(workloadDTO);
        HttpStatus addClientDataResult = result.getStatusCode();

        //assert that ClientController with given data returns a matching HttpStatus.OK
        assertThat(addClientDataResult)
                .isEqualTo(status);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .insertMultipleWorkloadData(workloadDTO);

    }

    @Test
    public void checkClientAvailabilityTest(){

        //given
        Long ID = 5L;
        Boolean availability = true;

        //when ClientService is triggered with given data, return a given Boolean value
        Mockito.when(clientService.isClientAvailable(ID))
                .thenReturn(availability);

        Boolean checkClientAvailabilityResult = clientController.checkClientAvailabilityById(ID);

        //assert that ClientController with given data returns a matching Boolean value
        assertThat(checkClientAvailabilityResult)
                .isEqualTo(availability);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .isClientAvailable(ID);

    }

    @Test
    public void changeFrequencyByClientIdTest(){

        //given
        Long ID = 5L;
        FrequencyDTO frequencyDTO = new FrequencyDTO();
        HttpStatus status = HttpStatus.OK;

        //when ClientService is triggered with given data, return a given HttpStatus.OK
        Mockito.when(clientService.changeFrequencyByClientId(ID, frequencyDTO))
                .thenReturn(status);

        ResponseEntity<?> result = clientController.changeFrequencyByClientId(ID, frequencyDTO);
        HttpStatus changeFrequencyByClientIdResult = result.getStatusCode();

        //assert that ClientController with given data returns a matching HttpStatus.OK
        assertThat(changeFrequencyByClientIdResult)
                .isEqualTo(status);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .changeFrequencyByClientId(ID, frequencyDTO);

    }

    @Test
    public void getClientFrequencySettingsTest(){

        //given
        Long ID = 5L;
        FrequencyDTO frequencyDTO = new FrequencyDTO();
        frequencyDTO.setCollectionFrequency(3D);

        //when ClientService is triggered with given data, return a given FrequencyDTO
        Mockito.when(clientService.getClientFrequencySettingsById(ID))
                .thenReturn(frequencyDTO);

        FrequencyDTO getClientFrequencySettingsResult = clientController.getClientFrequencySettingsById(ID);

        //assert that ClientController with given data returns FrequencyDTO with matching collection freq
        assertThat(getClientFrequencySettingsResult.getCollectionFrequency())
                .isEqualTo(frequencyDTO.getCollectionFrequency());

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .getClientFrequencySettingsById(ID);

    }

    @Test
    public void setConfigurationTest(){

        //given
        Long ID = 5L;
        HttpStatus status = HttpStatus.OK;
        ClientConfigDTO clientConfigDTO = new ClientConfigDTO();

        //when ClientService is triggered with given data, return a given HttpStatus.OK
        Mockito.when(clientService.setConfigurationById(ID, clientConfigDTO))
                .thenReturn(status);

        ResponseEntity result = clientController.setConfigurationByClientId(ID, clientConfigDTO);
        HttpStatus setConfigurationResult = result.getStatusCode();

        //assert that ClientController with given data returns matching HttpStatus.OK
        assertThat(setConfigurationResult)
                .isEqualTo(status);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .setConfigurationById(ID, clientConfigDTO);

    }

    @Test
    public void getClientByIdTest() throws Exception {

        //given
        Long ID = 5L;
        Boolean EXIST = true;
        String ALIAS = "Milos";
        Client client = new Client();
        client.setClientId(ID);
        client.setClientAllias(ALIAS);

        //when ClientService is triggered with given data, return a given Client object
        Mockito.when(clientService.findById(ID))
                .thenReturn(client);

        //when ClientService checks if client with id ID exists, return true
        Mockito.when(clientService.exists(ID))
                .thenReturn(EXIST);

        ResponseEntity result = clientController.getClientById(ID);
        Client getClientByIdResult = (Client) result.getBody();

        //assert that ClientController returns a matching Client
        assertThat(getClientByIdResult)
                .isEqualTo(client);

        //assert that ClientController returns a Client with matching alias
        assertThat(getClientByIdResult.getClientAllias())
                .isEqualTo(ALIAS);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .findById(ID);

    }

    @Test
    public void getChartDataByClientIdTest(){

        //given
        Long ID = 5L;
        Double CPU_USAGE = 3D;
        WorkloadData workloadData = new WorkloadData();
        workloadData.setCpuUsage(CPU_USAGE);
        List<WorkloadData> workloadDataList = new ArrayList<>();
        workloadDataList.add(workloadData);

        //when ClientService is triggered with given data, return a given list of objects
        Mockito.when(clientService.getAllDataById(ID))
                .thenReturn(workloadDataList);

        List<WorkloadData> getChartDataByClientIdResult = clientController.getChartDataByClientId(ID);

        //assert that ClientController returns non-empty list of objects
        assertThat(getChartDataByClientIdResult)
                .isNotEmpty();

        //assert that ClientController returns a matching list of WorkloadData
        assertThat(getChartDataByClientIdResult)
                .isEqualTo(workloadDataList);

        //assert that ClientController returns a list of WorkloadData with matching cpu usage at index 0
        assertThat(getChartDataByClientIdResult.get(0).getCpuUsage())
                .isEqualTo(CPU_USAGE);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .getAllDataById(ID);

    }

    @Test
    public void deleteClientById() throws Exception {

        //given
        Long ID = 5L;
        Boolean EXIST = true;
        Client client = new Client();
        HttpStatus status = HttpStatus.OK;

        //when ClientService checks if client with id ID exists, return true
        Mockito.when(clientService.exists(ID))
                .thenReturn(EXIST);

        //do nothing when ClientService is triggered with given data
        Mockito.doNothing()
                .when(clientService).deleteClientById(ID);

        ResponseEntity result = clientController.deleteClientById(ID);
        HttpStatus deleteClientByIdResult = result.getStatusCode();

        //assert that ClientController with given data returns matching HttpStatus.OK
        assertThat(deleteClientByIdResult)
                .isEqualTo(status);

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .deleteClientById(ID);

    }

}
