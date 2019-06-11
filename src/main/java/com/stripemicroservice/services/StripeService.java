package com.stripemicroservice.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripemicroservice.models.ChargeRequest;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "apikeys.properties")
public class StripeService {
	@Value("${stripe.keys.secret}")
	private String API_KEY_SECRET;
	
	public StripeService() {
	}
	
	@PostConstruct
	public void init() {
		Stripe.apiKey = API_KEY_SECRET;
	}
	
	public Charge charge(ChargeRequest chargeRequest) throws StripeException {
		Map<String, Object> chargeParams = new HashMap<>();
		chargeParams.put("amount", chargeRequest.getAmount());
		chargeParams.put("currency", chargeRequest.getCurrency());
		chargeParams.put("source", chargeRequest.getStripeToken());
		return Charge.create(chargeParams);
	}
}
