package at.fhwn.ma.serverapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

	final String CHANGE_FREQUENCIES_BY_CLIENT_ID = "/client/changeFrequencies/";
	final String SET_CONFIGURATION = "/setConfiguration";
	final String SEND_ECHO = "/client/echoResponse/";
	final int ECHO_VAL = 2;

	@Autowired
	private ClientDataRepository clientDataRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Override
	public List<Client> loadAll() {
		List<Client> clients = clientRepo.findAll();
		return clients;
	}

	public List<WorkloadData> getAllDataById(Long id) {
		// List<ClientInfo> clients = clientService.loadAll();
		// TODO transformisati listu klijenata u WorkloadData

		/*
		 * Client clientInfo = this.findById(id); List<ClientData> clientData =
		 * clientInfo.getClientData();
		 * 
		 * List<WorkloadData> clientWorkloadData = new ArrayList<>();
		 * 
		 * for (ClientData client : clientData) { WorkloadData workloadData = new
		 * WorkloadData(client); clientWorkloadData.add(workloadData); }
		 * 
		 * return clientWorkloadData;
		 */
		return null;
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
	public List<ClientData> createMultipleClients(WorkloadDTO workloadDataDTO) {
		List<ClientData> clientData = new ArrayList<>();

		for (WorkloadData workloadData : workloadDataDTO) {
			ClientData client = new ClientData();
			client.setCpuUsage(workloadData.getCpuUsage());
			client.setMemoryUsage(workloadData.getMemoryUsage());
			client.setTimestamp(workloadData.getTimestamp());

			ClientData clientDataCreated = clientDataRepo.save(client);
			clientData.add(clientDataCreated);
		}

		return clientData;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientDataRepo.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		Client clientInfo = clientRepo.findOne(id);
		return clientInfo;
	}

	@Transactional(readOnly = true)
	public boolean exists(Long id) {
		boolean client = clientRepo.exists(id);
		return client;
	}

	@Override
	public Boolean sendEcho(Client client) {

		System.out.println("ClientService.sendEcho for id " + client.getClientId());
		Boolean echo = false;

		String clientHost = ConnectionData.getClientHostById(client);

		String echoUrl = clientHost + SEND_ECHO + ECHO_VAL;

		System.out.println("-- clinetPath is " + echoUrl + " --");

		Double result = 3d;

		try {
			RestTemplate restTemplate = new RestTemplate();
			result = restTemplate.getForObject(echoUrl, Double.class);

		} catch (HttpStatusCodeException e) {
			String errorpayload = e.getResponseBodyAsString();
			System.out.println(errorpayload);

		} catch (RestClientException e) {
			System.out.println("no response payload, tell the user sth else ");
			System.out.println(e);
		}

		if (result == 4)
			echo = true;

		System.out.println(result);

		return echo;
	}

	@Override
	public Boolean isClientAvailable(Long id) {

		Boolean currentAvailability = false;

		Client client = clientRepo.findOne(id);

		if (client != null) {

			System.out.println("ClientService check if client " + client.getClientAllias() + " is available...");

			currentAvailability = this.sendEcho(client);
			this.updateAvailabilityStatus(id, currentAvailability);

		} else {

			System.out.println("ClientService.isClientAvailable: client " + id + " does not exist...");
		}

		return currentAvailability;
	}

	@Override
	public void updateAvailabilityStatus(Long id, Boolean currentAvailability) {

		Client client = clientRepo.findOne(id);
		client.setIsClientAvailable(currentAvailability);

		clientRepo.save(client);
	}

	@Override
	public FrequencyDTO getClientFrequencySettingsById(Long id) {

		Client client = clientRepo.findOne(id);

		Double collectionFrequency = client.getDataCollectionFrequency();
		Double uploadFrequency = client.getDataUploadFrequency();

		FrequencyDTO frequencyDTO = new FrequencyDTO(collectionFrequency, uploadFrequency);

		return frequencyDTO;

	}

	@Override
	public HttpStatus changeFrequencyByClientId(Long id, FrequencyDTO frequencyDTO) {

		System.out.println("ClientService.changeFrequencyByClientId for id " + id);
		System.out.println("New freq are");
		System.out.println(frequencyDTO);
		
		HttpStatus resultStatus = HttpStatus.NOT_MODIFIED;

		Client client = clientRepo.findOne(id);

		if (client != null) {

			String clientHost = ConnectionData.getClientHostById(client);

			String changeFreqUrl = clientHost + CHANGE_FREQUENCIES_BY_CLIENT_ID;

			System.out.println("-- changeFreqUrl is " + changeFreqUrl + " --");

			try {
				
				System.out.println("ClientService.changeFrequencyByClientId: Begin POST request");
				System.out.println("--- CHANGE FREQ PARAMS ---");
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				RestTemplate restTemplate = new RestTemplate();
						
				ResponseEntity<FrequencyDTO> postResponse = restTemplate.postForEntity(changeFreqUrl, frequencyDTO, FrequencyDTO.class);
				System.out.println("Response for Post Request: " + "\n" + postResponse.getBody());
				
				// saving updated values into a database
				client.setDataCollectionFrequency(frequencyDTO.getCollectionFrequency());
				client.setDataUploadFrequency(frequencyDTO.getUploadFrequency());

				clientRepo.save(client);
				
				return postResponse.getStatusCode();

			} catch (HttpStatusCodeException e) {
				String errorpayload = e.getResponseBodyAsString();
				System.out.println(errorpayload);
				return resultStatus;

			} catch (RestClientException e) {
				System.out.println("no response payload, tell the user sth else ");
				System.out.println(e);
				return resultStatus;
			}

		} else {

			System.out.println("Client with id " + id + " does not exist...");
			return resultStatus;
		}
	}

	@Override
	public HttpStatus setConfiguration(Long id, ClientConfigDTO clientConfigDTO) {

		Client client = clientRepo.findOne(id);

		HttpStatus resultStatus = HttpStatus.NOT_MODIFIED;

		if (client != null) {

			try {

				String clientHost = ConnectionData.getClientHostById(client);
				String configUrl = clientHost + SET_CONFIGURATION + "/" + clientConfigDTO.getIp() + "/"
						+ clientConfigDTO.getPort();

				System.out.println("-- configUrl is " + configUrl + " --");

				RestTemplate restTemplate = new RestTemplate();
				HttpStatus result = restTemplate.getForObject(configUrl, HttpStatus.class);

				// Save changed to DB
				client.setClientIp(clientConfigDTO.getIp());
				client.setClientPort(clientConfigDTO.getPort());

				clientRepo.save(client);

				return result;

			} catch (HttpStatusCodeException e) {
				String errorpayload = e.getResponseBodyAsString();
				System.out.println(errorpayload);
				return resultStatus;

			} catch (RestClientException e) {
				System.out.println("no response payload, tell the user sth else ");
				System.out.println(e);
				return resultStatus;
			}

		} else {

			System.out.println("ClientService.setConfiguration: client " + id + " does not exist...");
			return resultStatus;
		}
	}

	@Override
	public Long createClient(ClientDto clientDto) {

		Client client = new Client(clientDto);

		client = clientRepo.save(client);

		return client.getClientId();
	}

	public void insertWorkloadData(WorkloadData workloadData) {

		Double cpuUsage = workloadData.getCpuUsage();
		Double memoryUsage = workloadData.getMemoryUsage();
		Date timestamp = workloadData.getTimestamp();
		Long clientId = workloadData.getId();

		ClientData client = new ClientData(cpuUsage, memoryUsage, timestamp, clientId);

		clientDataRepo.save(client);

	}
}
