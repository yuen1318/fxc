package com.fxc.martrust.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fxc.martrust.fx_api.FxApi;
import com.fxc.martrust.fx_rate_service.FxRateService;
import com.fxc.martrust.web_client.WebClient;

@Component
public class Tester {

	@Autowired
	WebClient webClient;
	
	@Autowired
	FxApi fxApi;
	
	@Autowired
	FxRateService fxRateService;
	
	@PostConstruct
	private void testAnyThing() {
		//fast testing here
	}
	
}
