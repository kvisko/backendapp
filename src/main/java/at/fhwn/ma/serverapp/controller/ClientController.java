package at.fhwn.ma.serverapp.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.ClientDto;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.exception.CustomNotFoundException;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.service.ClientService;

@RestController
@RequestMapping("/api")
public class ClientController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	private ClientService clientService;

	/* For frontend: display all managed clients and their data */
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> getAllClients() {

	    logger.info("Get all clients from the database.");
		List<Client> clients = clientService.loadAll();
		logger.info("All clients successfully returned.");
		
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}

	/* For client registration at the point of the client installation */
	@RequestMapping(value = "/clients/createClient", method = RequestMethod.POST)
	public ResponseEntity<?> createClient(@RequestBody ClientDto clientDto) {

	    logger.info("===CREATE NEW CLIENT===");
	    logger.info("Provided parameters: client alias - {}, client IP - {}, client port - {}, " +
                "data collection frequency - {}, data upload frequency - {}.", clientDto.getClientAllias(),
                clientDto.getClientIp(), clientDto.getClientPort(), clientDto.getDataCollectionFrequency(),
                clientDto.getDataUploadFrequency());

	    Long id = clientService.createClient(clientDto);
        logger.info("===CLIENT CREATED WITH THE ID {}===", id);
		
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/clients/addSingleClientData", method = RequestMethod.POST)
	public ResponseEntity<?> addSingleClientData(@RequestBody WorkloadData workloadData) {
		
		logger.info("Add single WorkloadData for the client with the id {}.", workloadData.getId());
		clientService.insertWorkloadData(workloadData);
		logger.info("Workload data added.");
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/clients/addClientData", method = RequestMethod.POST)
	@Consumes({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> addClientData(@RequestBody WorkloadDTO workloadDataDTO) {

        logger.info("Add WorkloadDto for the client with the id {}.", workloadDataDTO.getClientId());
		clientService.insertMultipleWorkloadData(workloadDataDTO);
        logger.info("WorkloadDto added.");

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/* Check if client is available */
	@RequestMapping(value = "/clients/clientAvailability/{id}", method = RequestMethod.GET)
	public Boolean checkClientAvailabilityById(@PathVariable Long id) {
		
		logger.info("Check if the client with the id {} is available.", id);
		Boolean availability = clientService.isClientAvailable(id);
		logger.info("Client with the id {} is available - {}", id, availability);
		
		return availability;
	}

	@RequestMapping(value = "/clients/changeFrequencyByClientId/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> changeFrequencyByClientId(@PathVariable Long id,
			@RequestBody FrequencyDTO frequencyDTO) {

	    logger.info("Change data and collection frequencies for the client with the id {}.", id);
	    logger.info("Provided parameters: data collection frequency - {}, data upload frequency - {}."
                , frequencyDTO.getCollectionFrequency(), frequencyDTO.getUploadFrequency());
		HttpStatus result = clientService.changeFrequencyByClientId(id, frequencyDTO);
		logger.info("changeFrequencyByClientId - {}.", result.getReasonPhrase());

		return new ResponseEntity<>(result);
	}
	
	/* it works */
	@RequestMapping(value = "/clients/getClientFrequencySettings/{id}", method = RequestMethod.GET)
	public FrequencyDTO getClientFrequencySettingsById(@PathVariable Long id) {

	    logger.info("Get data and collection frequencies for the client with the id {}.", id);
		FrequencyDTO frequencyDTO = clientService.getClientFrequencySettingsById(id);
		logger.info("Frequencies for the client with the id {} successfully retrieved.", id);
		
		return frequencyDTO;
	}

	/* change client ip and client port. if change successful, save the settings to the dabase */
	@RequestMapping(value = "/clients/setConfiguration/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> setConfigurationByClientId(@PathVariable Long id, @RequestBody ClientConfigDTO clientConfigDTO) {

		logger.info("Set configuration for the client with the id {}.", id);
		logger.info("Provided parameters: client IP - {}, client port - {}", clientConfigDTO.getIp(),
                clientConfigDTO.getPort());
		HttpStatus result = clientService.setConfigurationById(id, clientConfigDTO);
        logger.info("setConfigurationById - {}.", result.getReasonPhrase());

		return new ResponseEntity<>(result);

	}
	
	

	
	/* OUT OF SCOPE */
	
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.GET)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<?> getClientById(@PathVariable Long id) throws Exception {

		if (id == null || !clientService.exists(id)) {
		    logger.info("Client with the id {} does not exist.", id);
			throw new CustomNotFoundException("NOT FOUND");
		}

        logger.info("Get client with the id {}", id);
		Client client = clientService.findById(id);
        logger.info("Client with the id {} successfully retrieved.", id);

		return new ResponseEntity<>(client, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getClientDataById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<WorkloadData> getChartDataByClientId(@PathVariable Long id) {

	    logger.info("Get all ClientData for the client with the id {}.", id);
		List<WorkloadData> clientData = clientService.getAllDataById(id);
        logger.info("ClientData for the client with the id {} successfully returned.", id);

		return clientData;
	}
	
	@RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteClientById(@PathVariable Long id) throws Exception {


		if (!clientService.exists(id)) {
            logger.info("Client with the id {} does not exist.", id);
			throw new CustomNotFoundException("NOT FOUND");
		}

        logger.info("Delete client with the id {}", id);
		clientService.deleteClientById(id);
		logger.info("Client with the id {} successfully deleted.", id);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
