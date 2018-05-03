package at.fhwn.ma.serverapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


public class WorkloadDTO implements Iterable<WorkloadData>{
	
	

	private List<WorkloadData> clientData = new ArrayList<>();
	
	public WorkloadDTO() {}

	public WorkloadDTO(List<WorkloadData> clientData) {
		this.clientData=clientData;
	}

	public List<WorkloadData> getClientData() {
		return clientData;
	}

	public void setClientData(List<WorkloadData> clientData) {
		this.clientData = clientData;
	}

	@Override
	public Iterator<WorkloadData> iterator() {
		// TODO Auto-generated method stub
		return clientData.iterator();
	}

	@Override
	public String toString() {
		return "WorkloadDTO [clientData=" + clientData + "]";
	}

	
	
	
	/*
	
	private double cpuUsage;
	private double memoryUsage;
	private Date timestamp;

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public double getMemoryUsage() {
		return memoryUsage;
	}

	public void setMemoryUsage(double memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getCpuUsageInPercentage() {
		return this.cpuUsage*100;
	}
	
	public double getMemoryUsageByDataUnit(String dataUnit) {
		double memoryUsageByDataUnit = 0;
		double mb = 1024*1024;
		if(dataUnit == "mb") {
			memoryUsageByDataUnit=this.memoryUsage/mb;
		}
		return memoryUsageByDataUnit;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder("timestamp: ");
		builder.append(this.getTimestamp());
		builder.append("  cpu usage: ");
        builder.append(this.getCpuUsageInPercentage());
        builder.append("  ram memory: ");
        builder.append(this.getMemoryUsageByDataUnit("mb"));

        return builder.toString();
	} */

}
