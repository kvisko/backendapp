package at.fhwn.ma.serverapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.JoinColumnOrFormula;

@Entity
@Table//(name = "client_info")
public class Client {

	@Id
	@GeneratedValue
	@Column(name = "client_id")
	private Long clientId;

	@NotNull
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

	//@OneToMany(mappedBy="client_data", targetEntity=ClientData.class)
	@OneToMany
	@JoinColumn(name = "client_id")
	private List<ClientData> clientData = new ArrayList<ClientData>();
	
	public Client() {
	}


}
