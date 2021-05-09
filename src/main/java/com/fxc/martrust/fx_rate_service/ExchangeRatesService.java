package com.fxc.martrust.fx_rate_service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.fxc.martrust.exception.InvalidReqBodyException;
import com.fxc.martrust.exception.UnhandledServiceException;
import com.fxc.martrust.fx_api.ConvertedCurrencyDto;
import com.fxc.martrust.fx_api.CurrencyDto;
import com.fxc.martrust.fx_api.FxApi;

@Service
final class ExchangeRatesService implements FxRateService{

	private FxApi fxApi;
	
	public ExchangeRatesService(FxApi fxApi) {
		super();
		this.fxApi = fxApi;
	}

	@Override
	public Map<String, Object> convertCurrency(Integer amount, String baseCurrencySymbol, String toCurrencySymbol) {

		List<CurrencyDto> currencies = fxApi.getCurrency();
		
		Optional<CurrencyDto> baseCurrency =currencies.stream().filter(x -> x.getName().equals(baseCurrencySymbol)).findFirst();
		
		if (baseCurrency.isPresent()) {
			Double toValueMultiplier = 1 / baseCurrency.get().getValue();
			
			CurrencyDto baseConvertedCurrency = currencies.stream().filter(x -> x.getName().equals(baseCurrencySymbol)).findFirst().get();
			baseConvertedCurrency.setValue( toValueMultiplier * baseConvertedCurrency.getValue());
			
			CurrencyDto toConvertedCurrency = currencies.stream().filter(x -> x.getName().equals(toCurrencySymbol)).findFirst().get();
			toConvertedCurrency.setValue(toValueMultiplier * toConvertedCurrency.getValue());
			
			
			Map<String, Object> resp = new HashMap<>();
			
			resp.put("data", ConvertedCurrencyDto.builder()
					.convertion(baseConvertedCurrency.getName() + "/" + toConvertedCurrency.getName())
					.value(roundToTwoDecimalPlace( (Double.valueOf(amount) * toConvertedCurrency.getValue()), "#.##"))
					.rate("1 = "+ roundToTwoDecimalPlace(toConvertedCurrency.getValue(), "#.##"))
					.build());
			
			return resp;

		} else {
			throw new UnhandledServiceException();
		}
	}
	

	@Override
	public Map<String, Object> getCurrencySymbol() {
		Map<String, Object> resp = new HashMap<>();
		resp.put("data", fxApi.getSymbols());
		return resp;
	}

	@Override
	public Map<String, Object> buy(BuyReqDto buyReqDto, BindingResult errors) {
		
		this.validateReqObject(errors);
		
		Map<String, Object> resp = new HashMap<>();
		JSONObject body = new JSONObject();
		body.put("status", "sucess");
		resp.put("data", body);
		return resp;
	}

	@Override
	public Map<String, Object> sell(SellReqDto sellReqDto, BindingResult errors) {
		
		this.validateReqObject(errors);
		
		Map<String, Object> resp = new HashMap<>();
		JSONObject body = new JSONObject();
		body.put("status", "sucess");
		resp.put("data", body);
		return resp;
	}

	private Double roundToTwoDecimalPlace(Double numberToRound, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return Double.valueOf(df.format(numberToRound));
	}
	
	private void validateReqObject(BindingResult errors) {

		if (errors.hasErrors()) {
			JSONArray errArr = new JSONArray();
			errors.getFieldErrors().forEach(x -> {
				JSONObject errObj = new JSONObject();
				errObj.put(x.getField(), x.getDefaultMessage());
				errArr.add(errObj);
			});
			throw new InvalidReqBodyException(errArr.toJSONString());
		}

	}
}
