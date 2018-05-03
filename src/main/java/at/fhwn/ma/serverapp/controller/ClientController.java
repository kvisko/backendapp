package at.fhwn.ma.serverapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.exception.CustomNotFoundException;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientInfo;
import at.fhwn.ma.serverapp.service.ClientService;
import at.fhwn.ma.serverapp.util.ResponseWrapper;

@RestController
@RequestMapping("/api")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> getAllClients() {
		List<ClientInfo> clients = clientService.loadAll();
		return new ResponseEntity<>(new ResponseWrapper(clients), HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> getClientById(@PathVariable Long id) throws Exception {
		if (id == null || !clientService.exists(id)) {
			throw new CustomNotFoundException("NOT FOUND");
		}
		ClientInfo client = clientService.findById(id);
		return new ResponseEntity<>(new ResponseWrapper(client), HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/addSingleClientData", method = RequestMethod.POST)
	public ResponseEntity<?> addSingleClientData(@RequestBody WorkloadData client) {
		clientService.create(client);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/createClientInfo", method = RequestMethod.POST)
	public ResponseEntity<?> createClientInfo(@RequestBody ClientInfo clientInfo) {

		clientService.createClientInfo(clientInfo);
		//TODO return ID
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/addClientData", method = RequestMethod.POST)
	@Consumes({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> addClientData(@RequestBody WorkloadDTO workloadDataDTO) {

		System.out.println("posting new data");
		System.out.println(workloadDataDTO.toString());

		clientService.createMultipleClients(workloadDataDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClientById(@PathVariable Long id) throws Exception {
		if (!clientService.exists(id)) {
			throw new CustomNotFoundException("NOT FOUND");
		}
		clientService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/clientAvailability", method = RequestMethod.GET)
	public Boolean checkClientAvailability(Long id) {
		Boolean availability = clientService.isClientAvailable(id);
		return availability;
	}

	@RequestMapping(value = "/clients/setFrequencies/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> setUploadAndCollectionFrequency(@PathVariable Long id,
			@RequestBody FrequencyDTO frequencyDTO) {

		clientService.setUploadAndCollectionFrequency(id, frequencyDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getClientDataById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<WorkloadData> getChartDataByClientId(@PathVariable Long id) {

		List<WorkloadData> clientData = new ArrayList<>();

		Client client1 = new Client(1L, 25D, 54D);

		WorkloadData data1 = new WorkloadData(client1);
		clientData.add(data1);
		client1.setCpuUsage(58D);
		client1.setMemoryUsage(79D);
		WorkloadData data2 = new WorkloadData(client1);
		clientData.add(data2);
		client1.setCpuUsage(8D);
		client1.setMemoryUsage(7D);
		WorkloadData data3 = new WorkloadData(client1);
		clientData.add(data3);
		client1.setCpuUsage(528D);
		client1.setMemoryUsage(179D);
		WorkloadData data4 = new WorkloadData(client1);
		clientData.add(data4);

		clientData = clientService.getAllDataById(id);

		return clientData;
	}

	@RequestMapping(value = "/clients/getClienSettings/{id}", method = RequestMethod.GET)
	public FrequencyDTO getClientFrequencySettings(@PathVariable Long id) {

		FrequencyDTO frequencyDTO = clientService.getClientFrequencySettings(id);
		return frequencyDTO;

	}

	@RequestMapping(value = "/clients/setConfiguration/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> setConfiguration(@PathVariable Long id, @RequestBody ClientConfigDTO clientConfigDTO) {

		clientService.setConfiguration(id, clientConfigDTO);

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
