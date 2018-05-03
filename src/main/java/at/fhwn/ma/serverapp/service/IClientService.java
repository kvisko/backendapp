package at.fhwn.ma.serverapp.service;

import java.util.List;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.ClientDto;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.model.ClientData;
import at.fhwn.ma.serverapp.model.Client;

public interface IClientService {

	List<Client> loadAll();
	
	List<WorkloadData> getAllDataById(Long id);

	void delete(Long id);

	Client findById(Long id);

//	void saveWorkloadData(List<WorkloadDataDTO> workloadDataDTOs);

	ClientData create(WorkloadData workloadData);

	List<ClientData> createMultipleClients(WorkloadDTO workloadDataDTO);
	
	Boolean isClientAvailable(Long id);

	void setUploadAndCollectionFrequency(Long id, FrequencyDTO frequencyDTO);

	void updateAvailabilityStatus(Long id, Boolean currentAvailability);

	FrequencyDTO getClientFrequencySettings(Long id);
	
	void setConfiguration(Long id, ClientConfigDTO clientConfigDTO);
	
	Long createClient(ClientDto clientDto);

	Boolean sendEcho(Client client);

}