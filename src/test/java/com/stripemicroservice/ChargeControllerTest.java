package com.stripemicroservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.model.Charge;
import com.stripemicroservice.controllers.ChargeController;
import com.stripemicroservice.models.ChargeRequest;
import com.stripemicroservice.services.StripeService;

@RunWith(SpringRunner.class)
@WebMvcTest(ChargeController.class)
@PropertySource("classpath:apikeys.properties")

public class ChargeControllerTest {
	@Value("${stripe.keys.public}")
	private String public_key;
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	StripeService stripeServiceMock;
	
	@Autowired
	private ObjectMapper mapper;
	
	private ChargeRequest chargeRequest;
	
	@Test
	public void testGetKey() throws Exception {
		mvc.perform(get("/get/publickey")
		.contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk())
		.andExpect(content().string(public_key));
	}
	
	@Test
	public void testCharge() throws Exception {
		Mockito.when(stripeServiceMock.charge(chargeRequest)).thenReturn(new Charge());
		chargeRequest = new ChargeRequest();
		chargeRequest.setAmount(500);
		chargeRequest.setCurrency(ChargeRequest.Currency.USD);
		chargeRequest.setStripeToken("tok_visa");
		
		mvc.perform(post("/charge")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(chargeRequest))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
	}
}
