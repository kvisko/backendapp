package at.fhwn.ma.serverapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "client_data")
public class ClientData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull 
	private Double cpuUsage;
	
	@NotNull
	private Double memoryUsage;
	
	@NotNull
	private Date timestamp;
	
	@NotNull
	@Column(name = "client_id")
	private Long clientId;
	    
	public ClientData() {}

	public ClientData(Double cpuUsage, Double memoryUsage, Date timestamp, Long clientId) {
		super();
		this.cpuUsage = cpuUsage;
		this.memoryUsage = memoryUsage;
		this.timestamp = timestamp;
		this.clientId = clientId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(Double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public Double getMemoryUsage() {
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


	

}
