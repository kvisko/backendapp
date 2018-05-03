package rs.test.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modelmapper.ModelMapper;

import rs.test.dto.WorkloadData;
import rs.test.model.Client;


public class ClientUT {
	
	private static final ModelMapper modelMapper = new ModelMapper();

	@Test
	public void checkClientMapping() {
		
		WorkloadData clientDto = new WorkloadData();
		clientDto.setId(2L);
		clientDto.setCpuUsage(25D);
		clientDto.setMemoryUsage(356D);
		
		Client client = modelMapper.map(clientDto, Client.class);
		assertEquals(clientDto.getId(), client.getId(),0.0001);
		assertEquals(clientDto.getCpuUsage(), client.getCpuUsage(), 0.0001);
		assertEquals(clientDto.getMemoryUsage(), client.getMemoryUsage(), 0.0001); 
		
		
	}
	
}
