package com.fxc.martrust.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fxc.martrust.fx_rate_service.BuyReqDto;
import com.fxc.martrust.fx_rate_service.FxRateService;
import com.fxc.martrust.fx_rate_service.SellReqDto;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class FxServiceControllerTest {

	private static MockMvc mockMvc;
	
	@Mock
	private FxRateService fxRateService;
	
	@InjectMocks
	private static FxServiceController fxServiceController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}
	
	//happy path since we're on time constraint
	
	@Test
	void testSymbol() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(fxServiceController).build();
		MockHttpServletRequestBuilder request = get("/fx-api/currency/symbol");
		this.mockMvc.perform(request).andExpect(status().isOk());
	}
	
	@Test
	void testConvert() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(fxServiceController).build();

		MultiValueMap<String, String> queryString = new LinkedMultiValueMap();
		queryString.add("from", "AUD");
		queryString.add("to", "EUR");
		queryString.add("amount", "200");
		
		MockHttpServletRequestBuilder request = get("/fx-api/currency/convert").queryParams(queryString);
		this.mockMvc.perform(request).andExpect(status().isOk());
	}

	
	@Test
	void testSell() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(fxServiceController).build();
		
		SellReqDto sellReqDto = SellReqDto.builder()
				.orderLevel(2d)
				.stake(1)
				.rollingSpot("EUR\\AUD")
				.build();
		
		System.out.println(this.objectToJsonString(sellReqDto));
		MockHttpServletRequestBuilder request = post("/fx-api/currency/sell").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectToJsonString(sellReqDto));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}
	
	@Test
	void testBuy() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(fxServiceController).build();
		
		BuyReqDto buyReqDto = BuyReqDto.builder()
				.orderLevel(2d)
				.stake(1)
				.rollingSpot("EUR\\AUD")
				.build();
		
		System.out.println(this.objectToJsonString(buyReqDto));
		MockHttpServletRequestBuilder request = post("/fx-api/currency/buy").contentType(MediaType.APPLICATION_JSON_VALUE).content(this.objectToJsonString(buyReqDto));
		this.mockMvc.perform(request).andExpect(status().isOk());
	}
	
	
	private String objectToJsonString(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(Include.ALWAYS);
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) { // ignored
		}
		return null;
	}
}
