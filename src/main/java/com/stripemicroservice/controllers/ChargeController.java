package com.stripemicroservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;
import com.stripemicroservice.models.ChargeRequest;
import com.stripemicroservice.services.StripeService;

@RestController
@PropertySource(ignoreResourceNotFound = true, value = "apikeys.properties")
public class ChargeController {

	@Value("${stripe.keys.public}")
	private String API_PUBLIC_KEY;
	
	@Autowired
	StripeService stripeService;
	
	@GetMapping("/get/publickey")
	public String retrievePublicKey() {
		return API_PUBLIC_KEY;
	}
	
	@PostMapping("/charge")
	public void charge(@RequestBody ChargeRequest chargeRequest) throws StripeException {
		stripeService.charge(chargeRequest);
	}
	
	@ExceptionHandler(StripeException.class)
	public String handleError(StripeException ex) {
		return ex.getMessage();
	}
	
}
