package at.fhwn.ma.serverapp.dto;


public class ClientDto {
	
	private String clientAllias;
	
	private String clientIp;

	private String clientPort;

	private Double dataCollectionFrequency;

	private Double dataUploadFrequency;

	private Boolean isClientAvailable;

	private String serverAllias;
	
	private String serverIp;

	private String serverPort;
	
	public ClientDto() {}

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
	
	

	

}
