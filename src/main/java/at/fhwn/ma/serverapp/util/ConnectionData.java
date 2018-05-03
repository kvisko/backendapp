package at.fhwn.ma.serverapp.util;

import at.fhwn.ma.serverapp.model.Client;

public class ConnectionData {
	
	public static final String CLIENT = "http://localhost:8080/client";

	public static String getClientHostById(Client client) {

		String clientHost = "http://" + client.getClientIp() + ":" + client.getClientPort();

		return clientHost;
	}


}
