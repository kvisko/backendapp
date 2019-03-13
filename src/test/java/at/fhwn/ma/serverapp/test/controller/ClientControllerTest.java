package at.fhwn.ma.serverapp.test.controller;

import at.fhwn.ma.serverapp.controller.ClientController;
import at.fhwn.ma.serverapp.dto.ClientDto;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.service.ClientService;
import at.fhwn.ma.serverapp.test.Util.ApplicationTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    public void getAllClients(){

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

        //assert that ClientController returns client with matching allias
        assertThat(clientSearchResult.get(0).getClientAllias())
                .isEqualTo(client.getClientAllias());

        //verify that the ClientService method is invoked certain amount of times
        Mockito.verify(clientService, Mockito.times(1))
                .loadAll();

    }

    @Test
    public void createClient(){

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

}
