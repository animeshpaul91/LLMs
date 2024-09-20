package com.animesh.springboot;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.animesh.springboot.services.PaymentService;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest // searches for a class marked with @SpringBootApplication to create an Application Context (container) with all beans.
// This context will help to autowire beans in this class to perform tests against.
class CoreApplicationTests {

	// Autowire/Inject Beans
	@Autowired
	private PaymentService paymentService;
	
	@Test
	void testDependencyInjection() {
		assertNotNull(paymentService);
	}

}
