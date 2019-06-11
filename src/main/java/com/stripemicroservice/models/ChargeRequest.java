package com.stripemicroservice.models;

public class ChargeRequest {
	private int amount;
	private Currency currency;
	private String stripeToken;
	
	public enum Currency {
		EUR,
		USD;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getStripeToken() {
		return stripeToken;
	}
	public void setStripeToken(String stripeToken) {
		this.stripeToken = stripeToken;
	}
	


}
