package rs.test;

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

import rs.test.model.Client;
import rs.test.model.ClientInfo;
import rs.test.repository.ClientInfoRepository;
import rs.test.service.ClientService;
import rs.test.service.IClientService;

@SpringBootApplication
public class AppDaoprojectApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(AppDaoprojectApplication.class, args);

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
