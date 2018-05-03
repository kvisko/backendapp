package rs.test.service;

import java.util.List;

import rs.test.dto.WorkloadData;
import rs.test.dto.ClientConfigDTO;
import rs.test.dto.FrequencyDTO;
import rs.test.dto.WorkloadDTO;
import rs.test.model.Client;
import rs.test.model.ClientInfo;

public interface IClientService {

	List<ClientInfo> loadAll();
	
	List<WorkloadData> getAllDataById(Long id);

	void delete(Long id);

	ClientInfo findById(Long id);

//	void saveWorkloadData(List<WorkloadDataDTO> workloadDataDTOs);

	Client create(WorkloadData workloadData);

	List<Client> createMultipleClients(WorkloadDTO workloadDataDTO);
	
	Boolean sendEcho(Long id);
	
	Boolean isClientAvailable(Long id);

	void setUploadAndCollectionFrequency(Long id, FrequencyDTO frequencyDTO);

	void updateAvailabilityStatus(Long id, Boolean currentAvailability);

	FrequencyDTO getClientFrequencySettings(Long id);
	
	void setConfiguration(Long id, ClientConfigDTO clientConfigDTO);
	
	Long createClientInfo(ClientInfo clientInfo);

}