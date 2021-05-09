package com.fxc.martrust.web_client;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.JSONParser;
import org.springframework.util.MultiValueMap;

import com.fxc.martrust.exception.UnhandledServiceException;

import lombok.NonNull;

/**
 * 
 * @author yuenyalung
 * @param <R1> HttpClient Object type
 * @param <R> Response Object type
 * 
 *	use as abstraction layer to leverage factory pattern if 
 *	multiple implementation will arise , so if we change the
 *	API we're using it won't break the code
 */
/**
 * 
 * @author yuenyalung
 *

 */
public interface WebClient<R1,R2> {

	public R1 createClient();
	
	public R2 getReq(@NonNull String apiPath, MultiValueMap<String, String> queryString);
	
	default Object parseJsonResp (JSONParser parser, String strToParse){
		try {
			return parser.parse(strToParse);
		} catch (Exception e) {
			throw new NullPointerException();
		} 
	}
	
	public final Function<HttpResponse, String> parseEntityUtil = resp -> {
		try {
			return EntityUtils.toString(resp.getEntity(), "UTF-8");
		} catch (Exception e) {
			throw new UnhandledServiceException(e);
		} 
	};
	
	public final BiFunction<JSONParser, String, Object> parseJsonResp = (parser, strToParse) -> {
		try {
			return parser.parse(strToParse);
		} catch (Exception e) {
			throw new UnhandledServiceException(e);
		} 
	};
}
