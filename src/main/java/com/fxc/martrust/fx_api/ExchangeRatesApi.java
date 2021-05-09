package com.fxc.martrust.fx_api;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fxc.martrust.web_client.WebClient;

/**
 * 
 * @author yuenyalung
 *	make class final for immutability and package level class
 *	to only call the interface and hide underlying logic
 */

@Component
final class ExchangeRatesApi implements FxApi{

	private WebClient webClient;
	
	public ExchangeRatesApi(WebClient webClient) {
		super();
		this.webClient = webClient;
	}

	@Override
	public List<CurrencyDto> getCurrency() {

		MultiValueMap<String, String> queryString = new LinkedMultiValueMap();
		queryString.add("access_key", "f5c612563eed3a059c15e95d6074ad10");
		String apiPath = "http://api.exchangeratesapi.io/v1/latest";
		JSONObject jsonResp = (JSONObject) webClient.getReq(apiPath, queryString);
		
		JSONObject rates = (JSONObject) jsonResp.get("rates");
		
		List<CurrencyDto> currencies = new ArrayList<>();
		
		for (Iterator iterator = rates.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Double value = Double.valueOf(rates.get(key).toString());
			currencies.add(CurrencyDto.builder().name(key).value(value).build());
		}
		
		return currencies;
	}

	@Override
	public List<CurrencySymbolDto> getSymbols() {

		MultiValueMap<String, String> queryString = new LinkedMultiValueMap();
		queryString.add("access_key", "f5c612563eed3a059c15e95d6074ad10");
		String apiPath = "http://api.exchangeratesapi.io/v1/symbols";
		JSONObject jsonResp = (JSONObject) webClient.getReq(apiPath, queryString);

		JSONObject symbols = (JSONObject) jsonResp.get("symbols");

		List<CurrencySymbolDto> currencySymbols = new ArrayList<>();

		for (Iterator iterator = symbols.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = symbols.get(key).toString();
			currencySymbols.add(CurrencySymbolDto.builder().name(key).description(value).build());
		}
		
		return currencySymbols;
	}

	
}
