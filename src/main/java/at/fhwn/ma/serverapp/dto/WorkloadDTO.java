package at.fhwn.ma.serverapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


public class WorkloadDTO implements Iterable<WorkloadData>{
	
	private Long clientId;

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

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "Client ID: " + clientId  + " WorkloadDTO [clientData=" + clientData + "]";
	}


}
