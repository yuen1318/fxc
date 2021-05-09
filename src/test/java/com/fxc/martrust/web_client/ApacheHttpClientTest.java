package com.fxc.martrust.web_client;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fxc.martrust.exception.UnhandledServiceException;

@ExtendWith(MockitoExtension.class)
class ApacheHttpClientTest {

	@InjectMocks
	private ApacheHttpClient apacheHttpClient;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	
	// need to transfer creating of client on another class to be able to mock it, but time constraint
	@Nested
	class CreateClient{
		@DisplayName("when createClient is invoke return value")
		@Test
		void testWhenCreateClientReturnValue() {
			assertNotNull(apacheHttpClient.createClient());
		}
		
	}
	
	@Nested
	class GetReq{
		
		
		@DisplayName("when get when status == 200 return value")
		@Test
		void testWhenGetStatusIs200ReturnValue() {
			MultiValueMap<String, String> queryString = new LinkedMultiValueMap();
			queryString.add("access_key", "f5c612563eed3a059c15e95d6074ad10");
			String apiPath = "http://api.exchangeratesapi.io/v1/latest";
			assertNotNull(apacheHttpClient.getReq(apiPath, queryString));
		}
		
		@DisplayName("when get when status != 200 throw error")
		@Test
		void testWhenGetStatusNot200ThrowError() {
			MultiValueMap<String, String> queryString = new LinkedMultiValueMap();
			String apiPath = "http://api.exchangeratesapi.io/v1/latest";
			assertThatThrownBy(() -> apacheHttpClient.getReq(apiPath, queryString)).isInstanceOf(UnhandledServiceException.class);
		}
		
	}
}
