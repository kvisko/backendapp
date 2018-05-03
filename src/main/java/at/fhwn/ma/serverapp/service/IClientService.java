package at.fhwn.ma.serverapp.service;

import java.util.List;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.FrequencyDTO;
import at.fhwn.ma.serverapp.dto.WorkloadDTO;
import at.fhwn.ma.serverapp.dto.WorkloadData;
import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientInfo;

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