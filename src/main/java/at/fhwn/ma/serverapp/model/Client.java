package at.fhwn.ma.serverapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ClientData")
public class Client {
	
	@Id
	@Column(name="client_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull @Column(name = "cpu_Usage")
	private Double cpuUsage;
	
	@NotNull @Column(name = "memory_Usage")
	private Double memoryUsage;
	
	@Column(name = "timestamp")
	private Date timestamp;
	
	@ManyToOne
	private ClientInfo clientInfo;

	public Client() {}
	
	public Client(Long clientId, Double cpuUsage, Double memoryUsage) {
		this.id=clientId;
		this.cpuUsage=cpuUsage;
		this.memoryUsage=memoryUsage;
	}
	
	public Client(Double cpuUsage, Double memoryUsage, Date timestamp) {
		super();
//		this.clientId = clientId;
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.timestamp=timestamp;
	} 
	
	public Long getId() {
		return id;
	}

	public void setId(Long clientId) {
		this.id = clientId;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(Double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(Double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ClientInfo getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
	
	

}
