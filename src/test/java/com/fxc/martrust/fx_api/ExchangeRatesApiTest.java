package com.fxc.martrust.fx_api;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.MultiValueMap;

import com.fxc.martrust.web_client.WebClient;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesApiTest {

	@Mock
	private WebClient webClient;
	
	@InjectMocks
	private ExchangeRatesApi exchangeRatesApi;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Nested
	class GetCurrency{
		
		@DisplayName("when getCurrency is invoke return value")
		@Test
		void testWhenGetCurrencyReturnValue() {
			
			JSONObject rates = new JSONObject();
			rates.put("AED", 4.46781d);
			rates.put("AFN", 93.52);
			
			JSONObject jsonResp = new JSONObject();
			jsonResp.put("rates", rates);
			
			when(webClient.getReq(ArgumentMatchers.anyString(), ArgumentMatchers.any(MultiValueMap.class))).thenReturn(jsonResp);
			
			assertNotNull(exchangeRatesApi.getCurrency());
		}
	}
	
	
	@Nested
	class GetSymbols{
		
		@DisplayName("when getSymbols is invoke return value")
		@Test
		void testWhenGetSymbolsReturnValue() {
			
			JSONObject symbols = new JSONObject();
			symbols.put("AED", "United Arab Emirates Dirham");
			symbols.put("AFN", "Afghan Afghani");
			
			JSONObject jsonResp = new JSONObject();
			jsonResp.put("symbols", symbols);
			
			when(webClient.getReq(ArgumentMatchers.anyString(), ArgumentMatchers.any(MultiValueMap.class))).thenReturn(jsonResp);
			
			assertNotNull(exchangeRatesApi.getSymbols());
		}
	}
}
