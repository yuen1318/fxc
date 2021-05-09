package com.fxc.martrust.fx_rate_service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;

import com.fxc.martrust.exception.InvalidReqBodyException;
import com.fxc.martrust.exception.UnhandledServiceException;
import com.fxc.martrust.fx_api.CurrencyDto;
import com.fxc.martrust.fx_api.CurrencySymbolDto;
import com.fxc.martrust.fx_api.FxApi;

@ExtendWith(MockitoExtension.class)
class ExchangeRatesServiceTest {

	@Mock
	private FxApi fxApi;
	
	@InjectMocks
	private ExchangeRatesService exchangeRatesService;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	
	@Nested
	class ConvertCurrency {

		private Integer amount = 1;
		private String baseCurrencySymbol = "AUD";
		private String toCurrencySymbol = "EUR";
		
		
		@DisplayName("when baseCurrency is empty should throw an error")
		@Test
		void testBaseCurrencyIsEmpty() {
			List<CurrencyDto> currencies = new ArrayList<>();
			when(fxApi.getCurrency()).thenReturn(currencies);
			assertThatThrownBy(() -> exchangeRatesService.convertCurrency(amount, baseCurrencySymbol, toCurrencySymbol )).isInstanceOf(UnhandledServiceException.class);
		}
		
		@DisplayName("when baseCurrency is valid return value")
		@Test
		void testWhenBaseCurrIsValidReturnValue() {
			
			List<CurrencyDto> currencies =  new ArrayList<>();
			CurrencyDto basedDto =  CurrencyDto.builder().name(baseCurrencySymbol).value(2d).build();
			CurrencyDto toDto =  CurrencyDto.builder().name(toCurrencySymbol).value(2d).build();
			currencies.add(basedDto);
			currencies.add(toDto);
			
			when(fxApi.getCurrency()).thenReturn(currencies);
			
			assertNotNull(exchangeRatesService.convertCurrency(amount, baseCurrencySymbol, toCurrencySymbol));
			
		}
	}
	
	
	@Nested
	class GetCurrencySymbol{
		
		@DisplayName("when getCurrencySymbol is invoke return value")
		@Test
		void testWhenGetCurrencySymbolReturnValue() {
			
			List<CurrencySymbolDto> currencySymbol =  new ArrayList<>();
			
			when(fxApi.getSymbols()).thenReturn(currencySymbol);
			
			assertNotNull(exchangeRatesService.getCurrencySymbol());
			
		}
	}
	
	@Nested
	class Buy{
		
		@DisplayName("when buy has invalid field throw error")
		@Test
		void testWhenBuyHasInvalidReqFieldThrowError() {
			BindingResult errors = Mockito.mock(BindingResult.class);
			//missing fields
			BuyReqDto invalidReqBody = BuyReqDto.builder().stake(1).build();
			when(errors.hasErrors()).thenReturn(true);
			assertThatThrownBy(() -> exchangeRatesService.buy(invalidReqBody, errors)).isInstanceOf(InvalidReqBodyException.class);
		}
		
		@DisplayName("when buy is invoke return value")
		@Test
		void testWhenBuyReturnValue() {
			BindingResult errors = Mockito.mock(BindingResult.class);
			//missing fields
			BuyReqDto validReqBody = BuyReqDto.builder().stake(1).orderLevel(2d).rollingSpot("EUR/AUD").build();
			when(errors.hasErrors()).thenReturn(false);
			
			assertNotNull(exchangeRatesService.buy(validReqBody, errors));
		}
	}
	
	@Nested
	class Sell{
		
		@DisplayName("when sell has invalid field throw error")
		@Test
		void testWhenSellHasInvalidReqFieldThrowError() {
			BindingResult errors = Mockito.mock(BindingResult.class);
			//missing fields
			SellReqDto invalidReqBody = SellReqDto.builder().stake(1).build();
			when(errors.hasErrors()).thenReturn(true);
			assertThatThrownBy(() -> exchangeRatesService.sell(invalidReqBody, errors)).isInstanceOf(InvalidReqBodyException.class);
		}
		
		@DisplayName("when sell is invoke return value")
		@Test
		void testWhenSellReturnValue() {
			BindingResult errors = Mockito.mock(BindingResult.class);
			//missing fields
			SellReqDto validReqBody = SellReqDto.builder().stake(1).orderLevel(2d).rollingSpot("EUR/AUD").build();
			when(errors.hasErrors()).thenReturn(false);
			
			assertNotNull(exchangeRatesService.sell(validReqBody, errors));
		}
	}
	
	
}
