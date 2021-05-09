package com.fxc.martrust.fx_api;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrencySymbolDto {
	private String name;
	private String description;
}
