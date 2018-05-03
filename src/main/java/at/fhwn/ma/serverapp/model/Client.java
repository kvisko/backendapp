package at.fhwn.ma.serverapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import at.fhwn.ma.serverapp.dto.ClientConfigDTO;
import at.fhwn.ma.serverapp.dto.ClientDto;

@Entity
@Table//(name = "client_info")
public class Client {

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	private Long clientId;

	@NotNull
	@Column(unique = true)
	private String clientAllias;
	
	@NotNull
	private String clientIp;

	@NotNull
	private String clientPort;

	@NotNull
	private Double dataCollectionFrequency;

	@NotNull
	private Double dataUploadFrequency;

	@NotNull
	private Boolean isClientAvailable;

	@NotNull
	private String serverAllias;
	
	@NotNull
	private String serverIp;

	@NotNull
	private String serverPort;

	@OneToMany
	@JoinColumn(name = "client_id")
	private List<ClientData> clientData = new ArrayList<ClientData>();
	
	public Client() {
	}
	

	public Client(ClientDto clientDto) {
		super();
		this.clientAllias = clientDto.getClientAllias();
		this.clientIp = clientDto.getClientIp();
		this.clientPort = clientDto.getClientPort();
		this.dataCollectionFrequency = clientDto.getDataCollectionFrequency();
		this.dataUploadFrequency = clientDto.getDataUploadFrequency();
		this.isClientAvailable = clientDto.getIsClientAvailable();
		this.serverAllias = clientDto.getServerAllias();
		this.serverIp = clientDto.getServerIp();
		this.serverPort = clientDto.getServerPort();
	}


	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientAllias() {
		return clientAllias;
	}

	public void setClientAllias(String clientAllias) {
		this.clientAllias = clientAllias;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientPort() {
		return clientPort;
	}

	public void setClientPort(String clientPort) {
		this.clientPort = clientPort;
	}

	public Double getDataCollectionFrequency() {
		return dataCollectionFrequency;
	}

	public void setDataCollectionFrequency(Double dataCollectionFrequency) {
		this.dataCollectionFrequency = dataCollectionFrequency;
	}

	public Double getDataUploadFrequency() {
		return dataUploadFrequency;
	}

	public void setDataUploadFrequency(Double dataUploadFrequency) {
		this.dataUploadFrequency = dataUploadFrequency;
	}

	public Boolean getIsClientAvailable() {
		return isClientAvailable;
	}

	public void setIsClientAvailable(Boolean isClientAvailable) {
		this.isClientAvailable = isClientAvailable;
	}

	public String getServerAllias() {
		return serverAllias;
	}

	public void setServerAllias(String serverAllias) {
		this.serverAllias = serverAllias;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public List<ClientData> getClientData() {
		return clientData;
	}

	public void setClientData(List<ClientData> clientData) {
		this.clientData = clientData;
	}

}
