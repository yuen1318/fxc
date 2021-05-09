package com.fxc.martrust.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fxc.martrust.fx_rate_service.BuyReqDto;
import com.fxc.martrust.fx_rate_service.FxRateService;
import com.fxc.martrust.fx_rate_service.SellReqDto;

import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/fx-api/currency")
public final class FxServiceController {

	private FxRateService fxRateService;

	public FxServiceController(FxRateService fxRateService) {
		super();
		this.fxRateService = fxRateService;
	}

	@ApiOperation("convert currency")
	@GetMapping("/convert")
	public ResponseEntity<Object> convertCurrency(
			@RequestParam(value = "from", required = true) String baseCurrencySymbol,
			@RequestParam(value = "to", required = true) String toCurrencySymbol,
			@RequestParam(value = "amount", required = true) Integer amount) {
		return ResponseEntity.status(200)
				.body(fxRateService.convertCurrency(amount, baseCurrencySymbol, toCurrencySymbol));
	}

	@ApiOperation("get currency symbols")
	@GetMapping("/symbol")
	public ResponseEntity<Object> getCurrencySymbols() {
		return ResponseEntity.status(200).body(fxRateService.getCurrencySymbol());
	}

	@ApiOperation("buy")
	@PostMapping("/buy")
	public ResponseEntity<Object> buy(@Valid @RequestBody(required = true) BuyReqDto reqBody, BindingResult errors) {
		return ResponseEntity.status(200).body(fxRateService.buy(reqBody, errors));
	}

	@ApiOperation("sell")
	@PostMapping("/sell")
	public ResponseEntity<Object> sell(@Valid @RequestBody(required = true) SellReqDto reqBody, BindingResult errors) {
		return ResponseEntity.status(200).body(fxRateService.sell(reqBody, errors));
	}
	
}
