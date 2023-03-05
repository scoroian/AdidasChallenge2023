package com.adidas.backend.publicservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adidas.backend.publicservice.controller.PublicServiceController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PublicServiceController.class)
class DummyRestControllerTests {

	@Autowired
	private PublicServiceController dummyRestController;

	@Test
	void testDummyController() {
		String expectedResponse = "Hello, this is a dummy response from public service";
		ResponseEntity<String> response = dummyRestController.getUser("asd@adiclub.com");
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(expectedResponse, response.getBody());
	}

}
