package com.fxc.martrust.fx_api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CurrencyDto {
	private String name;
	private Double value;
}
