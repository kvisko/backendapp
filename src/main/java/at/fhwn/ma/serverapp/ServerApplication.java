package at.fhwn.ma.serverapp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import at.fhwn.ma.serverapp.model.Client;
import at.fhwn.ma.serverapp.model.ClientInfo;
import at.fhwn.ma.serverapp.repository.ClientInfoRepository;
import at.fhwn.ma.serverapp.service.ClientService;
import at.fhwn.ma.serverapp.service.IClientService;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(ServerApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
