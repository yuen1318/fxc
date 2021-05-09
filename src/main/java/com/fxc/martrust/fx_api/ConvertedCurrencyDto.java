package com.fxc.martrust.fx_api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConvertedCurrencyDto {
	private String rate;
	private String convertion;
	private Double value;
}
