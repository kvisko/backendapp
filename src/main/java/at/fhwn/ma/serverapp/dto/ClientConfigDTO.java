package at.fhwn.ma.serverapp.dto;

public class ClientConfigDTO {
	
	private String ip;
	private String port;
	
	public ClientConfigDTO() {}

	public ClientConfigDTO(String ip, String port) {
		this.ip=ip;
		this.port=port;
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

}
