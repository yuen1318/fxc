package com.fxc.martrust.fx_rate_service;

import java.util.Map;

import org.springframework.validation.BindingResult;

/**
 * 
 * @author yuenyalung
 *	make class final for immutability and package level class
 *	to only call the interface and hide underlying logic
 */
public interface FxRateService {

	public Map<String, Object> convertCurrency(Integer amount, String baseCurrencySymbol, String toCurrencySymbol);
	public Map<String, Object> getCurrencySymbol();
	public Map<String, Object> buy(BuyReqDto buyReqDto, BindingResult errors);
	public Map<String, Object> sell(SellReqDto reqBody, BindingResult errors);
	
}
