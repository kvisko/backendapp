package at.fhwn.ma.serverapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<?> loadAll() {
		System.out.println(":::::: loadAll");
		
		return null;
	}
}
