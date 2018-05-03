package at.fhwn.ma.serverapp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.glassfish.hk2.utilities.reflection.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.repository.ClientInfoRepository;
import at.fhwn.ma.serverapp.repository.ClientRepository;
import at.fhwn.ma.serverapp.util.ConnectionData;

@Service
public class ClientService implements IClientService {

	final String SET_FREQUENCIES = "/setFrequencies/";
	final String SET_CONFIGURATION = "setConfiguration";

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientInfoRepository clientInfoRepository;

	@Override
	public List<Client> loadAll() {
		List<Client> clients = clientInfoRepository.findAll();
		return clients;
	}

	public List<WorkloadData> getAllDataById(Long id) {
		// List<ClientInfo> clients = clientService.loadAll();
		// TODO transformisati listu klijenata u WorkloadData

		/*
		Client clientInfo = this.findById(id);
		List<ClientData> clientData = clientInfo.getClientData();

		List<WorkloadData> clientWorkloadData = new ArrayList<>();

		for (ClientData client : clientData) {
			WorkloadData workloadData = new WorkloadData(client);
			clientWorkloadData.add(workloadData);
		}

		return clientWorkloadData;
		*/
		return null;
	}

	@Override
	@Transactional
	public ClientData create(WorkloadData workloadData) {
		ClientData client = new ClientData();
		client.setCpuUsage(workloadData.getCpuUsage());
		client.setMemoryUsage(workloadData.getMemoryUsage());

		ClientData clientCreated = clientRepository.save(client);
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

			ClientData clientDataCreated = clientRepository.save(client);
			clientData.add(clientDataCreated);
		}

		return clientData;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clientRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Client findById(Long id) {
		Client clientInfo = clientInfoRepository.findOne(id);
		return clientInfo;
	}

	@Transactional(readOnly = true)
	public boolean exists(Long id) {
		boolean client = clientInfoRepository.exists(id);
		return client;
	}

	@Override
	public Boolean sendEcho(Long id) {

		// ClientInfo client = clientService.findById(id);
		// String hostAddress = client.getAddress();

		final String SEND_ECHO = "/sendEcho/";
		double value = 2;
		Boolean echo = true;
		
		/*
		 * RestTemplate restTemplate = new RestTemplate();
		 * MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new
		 * MappingJackson2HttpMessageConverter(); //
		 * mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
		 * MediaType.APPLICATION_JSON));
		 * restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
		 * 
		 *GENERATING TIMEOUT
		 * HttpComponentsClientHttpRequestFactory rf =
			    (HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory();
			rf.setReadTimeout(1 * 1000);
			rf.setConnectTimeout(1 * 1000);
		 *GENERATING TIMEOUT 
		 * 
		 * HttpHeaders headers = new HttpHeaders(); headers.add("Content-Type",
		 * "application/json"); // headers.setContentType(MediaType.APPLICATION_JSON);
		 * String postUrl = ConnectionData.CLIENT + SEND_ECHO + id; HttpEntity<Double>
		 * request = new HttpEntity<>(value, headers); restTemplate.exchange(postUrl,
		 * HttpMethod.POST, request, new ParameterizedTypeReference<Double>() {});
		 * 
		 * double responseValue = request.getBody(); if (responseValue == 4) { echo =
		 * true; } else { echo = false; }
		 */
		return echo;

		/*
		 * Process p1 = java.lang.Runtime.getRuntime().exec("ping -n 1 www.google.com");
		 * int returnVal = p1.waitFor(); boolean reachable = (returnVal==0);
		 */

		/*
		 * try {
		 * 
		 * InetAddress address = InetAddress.getByName(hostAddress);
		 * System.out.println("Name: " + address.getHostName());
		 * System.out.println("Address: " + address.getHostAddress());
		 * if(address.isReachable(3000)==true) {
		 * 
		 * System.out.println("Client is available");
		 * client.setClientAvailability(true); } else
		 * client.setClientAvailability(false); }
		 * 
		 * catch (UnknownHostException e) { System.err.println("Client is unavailable");
		 * client.setClientAvailability(false); }
		 * 
		 * catch (IOException e) { System.err.println("Unable to connect"); }
		 */

		// return client.getIsClientAvailable();

	}

	@Override
	public Boolean isClientAvailable(Long id) {
		Boolean currentAvailability = this.sendEcho(id);
		this.updateAvailabilityStatus(id, currentAvailability);

		return currentAvailability;
	}

	@Override
	public void updateAvailabilityStatus(Long id, Boolean currentAvailability) {
		
		/*
		Client client = this.findById(id);
		client.setIsClientAvailable(clientRepository.save(currentAvailability));
		
		*/
	}

	@Override
	public FrequencyDTO getClientFrequencySettings(Long id) {

		/*Client client = clientInfoRepository.findOne(id);

		Double collectionFrequency = client.getDataCollectionFrequency();
		Double uploadFrequency = client.getDataUploadFrequency();
		FrequencyDTO frequencyDTO = new FrequencyDTO(collectionFrequency, uploadFrequency);

		return frequencyDTO;*/
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
		Client client = clientInfoRepository.findOne(id);
		client.setDataCollectionFrequency(frequencyDTO.getCollectionFrequency());
		client.setDataUploadFrequency(frequencyDTO.getUploadFrequency());

		clientInfoRepository.save(client);
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
		Client client = clientInfoRepository.findOne(id);
		client.setIp(clientConfigDTO.getIp());
		client.setPort(clientConfigDTO.getPort());

		clientInfoRepository.save(client);
		
		*/

	}

	@Override
	public Long createClientInfo(Client clientInfo) {

		/*
		clientInfoRepository.save(clientInfo);

		return clientInfo.getId();
		*/
		return null;
	}
}
