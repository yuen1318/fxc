package com.fxc.martrust.fx_api;

import java.util.List;

/**
 * 
 * @author yuenyalung
 *	use as abstraction layer to leverage factory pattern if 
 *	multiple implementation will arise , so if we change the
 *	API we're using it won't break the code
 */
public interface FxApi {
	public List<CurrencyDto> getCurrency();
	public List<CurrencySymbolDto> getSymbols();
}
