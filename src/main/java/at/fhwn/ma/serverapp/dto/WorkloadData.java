package at.fhwn.ma.serverapp.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import at.fhwn.ma.serverapp.model.ClientData;

public class WorkloadData {

	private Long id;
	@NotNull
	private Double cpuUsage;
	@NotNull
	private Double memoryUsage;

	private Date timestamp;

	// ___________________________________

	private Double totalRam;

	private String operatingSystem;

	// ___________________________________

	public WorkloadData() {
	}

	public WorkloadData(ClientData client) {
		this.id = client.getId();
		this.cpuUsage = client.getCpuUsage();
		this.memoryUsage = client.getMemoryUsage();
		this.timestamp = client.getTimestamp();
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

	public Double getTotalRam() {
		return totalRam;
	}

	public void setTotalRam(Double totalRam) {
		this.totalRam = totalRam;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	@Override
	public String toString() {
		return "ClientDTO [id=" + id + ", cpuUsage=" + cpuUsage + ", memoryUsage=" + memoryUsage + ", timestamp="
				+ timestamp + ", totalRam=" + totalRam + ", operatingSystem=" + operatingSystem + "]";
	}

}
