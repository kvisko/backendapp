package at.fhwn.ma.serverapp.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	final String SET_FREQUENCIES = "/setFrequencies/";
	final String SET_CONFIGURATION = "setConfiguration";
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

		// ClientInfo client = clientService.findById(id);
		// String hostAddress = client.getAddress();
		
		System.out.println("ClientService.sendEcho for id "+client.getClientId());
		
		Boolean echo = false;
		
		String clientHost = ConnectionData.getClientHostById(client);
		
		String echoUrl = clientHost + SEND_ECHO + ECHO_VAL;
		
		System.out.println("-- clinetPath is "+ echoUrl + " --");
		
		Double result = 3d;
		
		try{
			 RestTemplate restTemplate = new RestTemplate();
			 result = restTemplate.getForObject(echoUrl, Double.class);
			 
		} catch(HttpStatusCodeException e){
		     String errorpayload = e.getResponseBodyAsString();
		     System.out.println(errorpayload);
		     
		} catch(RestClientException e){
			  System.out.println("no response payload, tell the user sth else ");
			  System.out.println(e);
		}
		
	    if(result == 4)
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
	public FrequencyDTO getClientFrequencySettings(Long id) {

		/*
		 * Client client = clientInfoRepository.findOne(id);
		 * 
		 * Double collectionFrequency = client.getDataCollectionFrequency(); Double
		 * uploadFrequency = client.getDataUploadFrequency(); FrequencyDTO frequencyDTO
		 * = new FrequencyDTO(collectionFrequency, uploadFrequency);
		 * 
		 * return frequencyDTO;
		 */
		return null;
	}

	@Override
	public void setUploadAndCollectionFrequency(Long id, FrequencyDTO frequencyDTO) {

		// prvi korak, kontaktirati klijenta
		// apdejtovati library u dependencies
		/*
		 * 
		 * RestTemplate restTemplate = new RestTemplate();
		 * MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new
		 * MappingJackson2HttpMessageConverter(); //
		 * mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
		 * MediaType.APPLICATION_JSON));
		 * restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
		 * "application/json"); // headers.setContentType(MediaType.APPLICATION_JSON);
		 * String postUrl = ConnectionData.CLIENT + SET_FREQUENCIES + id;
		 * HttpEntity<Object> request = new HttpEntity<>(frequencyDTO, headers);
		 * restTemplate.exchange(postUrl, HttpMethod.PUT, request, new
		 * ParameterizedTypeReference<FrequencyDTO>() {});
		 */
		// saving updated values into a database

		/*
		 * Client client = clientInfoRepository.findOne(id);
		 * client.setDataCollectionFrequency(frequencyDTO.getCollectionFrequency());
		 * client.setDataUploadFrequency(frequencyDTO.getUploadFrequency());
		 * 
		 * clientInfoRepository.save(client);
		 */
	}

	@Override
	public void setConfiguration(Long id, ClientConfigDTO clientConfigDTO) {

		/*
		 * RestTemplate restTemplate = new RestTemplate();
		 * 
		 * MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new
		 * MappingJackson2HttpMessageConverter(); //
		 * mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
		 * MediaType.APPLICATION_JSON));
		 * restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
		 * "application/json"); // headers.setContentType(MediaType.APPLICATION_JSON);
		 * String postUrl = ConnectionData.CLIENT + SET_CONFIGURATION + id;
		 * HttpEntity<Object> request = new HttpEntity<>(clientConfigDTO, headers);
		 * restTemplate.exchange(postUrl, HttpMethod.PUT, request, new
		 * ParameterizedTypeReference<ClientConfigDTO>() {}); }
		 */

		/*
		 * Client client = clientInfoRepository.findOne(id);
		 * client.setIp(clientConfigDTO.getIp());
		 * client.setPort(clientConfigDTO.getPort());
		 * 
		 * clientInfoRepository.save(client);
		 * 
		 */

	}

	@Override
	public Long createClient(ClientDto clientDto) {

		Client client = new Client(clientDto);

		client = clientRepo.save(client);

		return client.getClientId();
	}
}
