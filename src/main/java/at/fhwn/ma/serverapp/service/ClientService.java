package at.fhwn.ma.serverapp.service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.ClientDto;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import at.fhwn.ma.serverapp.util.ConnectionData;
import at.fhwn.ma.serverapp.repository.ClientDataRepository;

@Service
public class ClientService implements IClientService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	final String CHANGE_FREQUENCIES_BY_CLIENT_ID = "/client/changeFrequencies/";
	final String SET_CONFIGURATION = "/setConfiguration";
	final String SEND_ECHO = "/client/echoResponse/";
	final int ECHO_VAL = 2;

	@Autowired
	private ClientDataRepository clientDataRepo;

	@Autowired
	private ClientRepository clientRepo;

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<Client> loadAll() {

	    logger.debug("Retrieving all clients from the database...");
		List<Client> clients = clientRepo.findAll();

		return clients;
	}

	public List<WorkloadData> getAllDataById(Long id) {

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);
		List<ClientData> clientDataList = client.getClientData();

		List<WorkloadData> workloadDataList = new ArrayList<>();

		logger.debug("Fetching all client's ClientData...");
		for(ClientData clientData: clientDataList){

			WorkloadData workloadData = new WorkloadData(clientData);
			workloadDataList.add(workloadData);

		}
		logger.debug("ClientData fetched.");

		return workloadDataList;
	}

	@Override
	@Transactional
	public ClientData create(WorkloadData workloadData) {

		ClientData client = new ClientData();
		client.setCpuUsage(workloadData.getCpuUsage());
		client.setMemoryUsage(workloadData.getMemoryUsage());

		ClientData clientCreated = clientDataRepo.save(client);
		return clientCreated;
	}

	@Override
	@Transactional
	public List<ClientData> insertMultipleWorkloadData(WorkloadDTO workloadDataDTO) {

        logger.debug("Create new list of ClientData.");
		List<ClientData> toBeInsertedClientData = new ArrayList<>();

		logger.debug("Iterating through the WorkloadDto...");
		for (WorkloadData workloadData : workloadDataDTO) {
			
			ClientData client = new ClientData();
			
			client.setClientId(workloadDataDTO.getClientId());
			
			client.setCpuUsage(workloadData.getCpuUsage());
			client.setMemoryUsage(workloadData.getMemoryUsage());
			client.setTimestamp(workloadData.getTimestamp());

			toBeInsertedClientData.add(client);
		}
		logger.debug("WorkloadDto data added to the list of ClientData.");

		logger.debug("Persist list of ClientData.");
        List<ClientData> insertedData = clientDataRepo.save(toBeInsertedClientData);
		logger.debug("List of ClientData persisted.");

		return insertedData;
	}

	@Override
	@Transactional
	public void deleteClientById(Long id) {

	    logger.debug("Deleting client with the id {} from the database.", id);
		clientRepo.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client clientInfo = clientRepo.findOne(id);

		return clientInfo;
	}

	@Transactional(readOnly = true)
	public boolean exists(Long id) {

	    logger.debug("Checking if clients with the id {} exists in the database...", id);
		boolean client = clientRepo.exists(id);

		return client;
	}

	@Override
	public Boolean sendEcho(Client client) {

		Boolean echo = false;

		String clientHost = ConnectionData.getClientHostById(client);

		String echoUrl = clientHost + SEND_ECHO + ECHO_VAL;

		logger.debug("Client URL: {}", echoUrl);

		Double result = 3d;

		try {
		    logger.debug("Generating the getForObject method...");
			result = restTemplate.getForObject(echoUrl, Double.class);
			logger.debug("Getting the response from the client...");

		} catch (HttpStatusCodeException e) {
		    logger.warn(e.getResponseBodyAsString());
			String errorpayload = e.getResponseBodyAsString();
			System.out.println(errorpayload);

		} catch (RestClientException e) {
		    logger.warn("No response payload", e);
		}

		logger.debug("Evaluating the response...");
		if (result == 4)
			echo = true;

		return echo;
	}

	@Override
	public Boolean isClientAvailable(Long id) {

		Boolean currentAvailability = false;

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);

		if (client != null) {

            logger.debug("Send echo request to the client {}.", client.getClientAllias());
			currentAvailability = this.sendEcho(client);
			this.updateAvailabilityStatus(id, currentAvailability);

		} else {

		    logger.info("Client with the id {} does not exist.", id);

		}

		return currentAvailability;
	}

	@Override
	public void updateAvailabilityStatus(Long id, Boolean currentAvailability) {

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);
		logger.debug("Updating client's availability...");
		client.setIsClientAvailable(currentAvailability);

		clientRepo.save(client);
	}

	@Override
	public FrequencyDTO getClientFrequencySettingsById(Long id) {

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);

		logger.debug("Retrieve client's frequencies.");
		Double collectionFrequency = client.getDataCollectionFrequency();
		Double uploadFrequency = client.getDataUploadFrequency();

		logger.debug("Creating FrequencyDTO with retrieved frequencies for the response.");
		logger.info("Client - {}. Data collection frequency - {}, data upload frequency - {}.", id, collectionFrequency, uploadFrequency);
		FrequencyDTO frequencyDTO = new FrequencyDTO(collectionFrequency, uploadFrequency);

		return frequencyDTO;
	}

	@Override
	public HttpStatus changeFrequencyByClientId(Long id, FrequencyDTO frequencyDTO) {

		HttpStatus resultStatus = HttpStatus.NOT_MODIFIED;

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);

		if (client != null) {

			String clientHost = ConnectionData.getClientHostById(client);

			String changeFreqUrl = clientHost + CHANGE_FREQUENCIES_BY_CLIENT_ID;

            logger.debug("Client URL: {}", changeFreqUrl);

			try {
				
				logger.debug("Generating HTTP header.");
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				logger.debug("Generating the postForEntity method...");
				ResponseEntity<FrequencyDTO> postResponse = restTemplate.postForEntity(changeFreqUrl, frequencyDTO, FrequencyDTO.class);
				logger.debug("Getting the post method response...");

				// saving updated values into a database
                logger.debug("Persist new frequencies into the database.");
				client.setDataCollectionFrequency(frequencyDTO.getCollectionFrequency());
				client.setDataUploadFrequency(frequencyDTO.getUploadFrequency());

				clientRepo.save(client);
				logger.info("New frequencies persisted for the client with the id {}.", id);
				
				return postResponse.getStatusCode();

			} catch (HttpStatusCodeException e) {
				String errorpayload = e.getResponseBodyAsString();
                logger.warn(errorpayload);
				return resultStatus;

			} catch (RestClientException e) {
				logger.warn("No response payload.", e);
				return resultStatus;
			}

		} else {

            logger.info("Client with the id {} does not exist.", id);

			return resultStatus;
		}
	}

	@Override
	public HttpStatus setConfigurationById(Long id, ClientConfigDTO clientConfigDTO) {

        logger.debug("Retrieving client with the id {} from the database...", id);
		Client client = clientRepo.findOne(id);

		HttpStatus resultStatus = HttpStatus.NOT_MODIFIED;

		if (client != null) {

			try {

				String clientHost = ConnectionData.getClientHostById(client);
				String configUrl = clientHost + SET_CONFIGURATION + "/" + clientConfigDTO.getIp() + "/"
						+ clientConfigDTO.getPort();

                logger.debug("Client URL: {}", configUrl);

                logger.debug("Generating the getForObject method...");
				HttpStatus result = restTemplate.getForObject(configUrl, HttpStatus.class);
                logger.debug("Getting the get method response...");

				// Save changed to DB
                logger.debug("Persist new configuration parameters into the database.");
				client.setClientIp(clientConfigDTO.getIp());
				client.setClientPort(clientConfigDTO.getPort());

				clientRepo.save(client);
				logger.debug("New configuration parameters persisted for the client with the id {}.", id);

				return result;

			} catch (HttpStatusCodeException e) {
				String errorpayload = e.getResponseBodyAsString();
				logger.warn(errorpayload);
				return resultStatus;

			} catch (RestClientException e) {
				logger.warn("No response payload.", e);
				return resultStatus;
			}

		} else {

            logger.info("Client with the id {} does not exist.", id);
			return resultStatus;
		}
	}

	@Override
	public Long createClient(ClientDto clientDto) {

		logger.debug("Create new Client object with ClientDto data.");
		Client client = new Client(clientDto);

		logger.debug("Persist client object into the database.");
		client = clientRepo.save(client);
		logger.debug("Client persisted.");

		return client.getClientId();
	}

	public void insertWorkloadData(WorkloadData workloadData) {

		logger.debug("Extract WorkloadData parameters.");
		Double cpuUsage = workloadData.getCpuUsage();
		Double memoryUsage = workloadData.getMemoryUsage();
		Date timestamp = workloadData.getTimestamp();
		Long clientId = workloadData.getId();

		logger.debug("Create new ClientData object with WorkloadData parameters");
		ClientData client = new ClientData(cpuUsage, memoryUsage, timestamp, clientId);

		logger.debug("Persist ClientData object into the database");
		clientDataRepo.save(client);
		logger.debug("ClientData persisted.");

	}
}
