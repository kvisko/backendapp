package rs.test.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "client_info")
public class ClientInfo {

	@Id
	@GeneratedValue
	@Column(name = "client_info_id")
	private Long id;

	// @NotNull
	private String ip;

	// @NotNull
	private String port;

	// @NotNull
	private Double dataCollectionFrequency;

	// @NotNull
	private Double dataUploadFrequency;

	private Boolean isClientAvailable;

	private String serverIp;

	private String serverPort;

	private String clientAllias;

	private String serverAllias;

	@OneToMany
	private List<Client> clientData = new ArrayList<>();

	public ClientInfo() {
	}

	public ClientInfo(String ip, String port, Double dataCollectionFrequency, Double dataUploadFrequency,
			Boolean isClientAvailable, String serverIp, String serverPort, String clientAllias, String serverAllias,
			List<Client> clientData) {
		super();
		this.ip = ip;
		this.port = port;
		this.dataCollectionFrequency = dataCollectionFrequency;
		this.dataUploadFrequency = dataUploadFrequency;
		this.isClientAvailable = isClientAvailable;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.clientAllias = clientAllias;
		this.serverAllias = serverAllias;
		this.clientData = clientData;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
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

	public String getClientAllias() {
		return clientAllias;
	}

	public void setClientAllias(String clientAllias) {
		this.clientAllias = clientAllias;
	}

	public String getServerAllias() {
		return serverAllias;
	}

	public void setServerAllias(String serverAllias) {
		this.serverAllias = serverAllias;
	}

	public List<Client> getClientData() {
		return clientData;
	}

	public void setClientData(List<Client> clientData) {
		this.clientData = clientData;
	}

}
