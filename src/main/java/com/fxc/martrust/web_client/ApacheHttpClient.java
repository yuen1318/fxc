package com.fxc.martrust.web_client;

import java.io.IOException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.fxc.martrust.exception.UnhandledServiceException;

import lombok.NonNull;

/**
 * 
 * @author yuenyalung make class final for immutability and package level class
 *         to only call the interface and hide underlying logic
 */
@Component
final class ApacheHttpClient implements WebClient<CloseableHttpClient, JSONObject> {

	private int timeoutSec = 120;

	@Description("SSL enabled with 2 min of timeout")
	@Override
	public CloseableHttpClient createClient() {

		try {
			int timeout = this.timeoutSec; // seconds (2 minutes)
			RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout * 1000)
					.setConnectionRequestTimeout(timeout * 1000).setSocketTimeout(timeout * 1000).build();

			// Creating SSLContextBuilder object
			SSLContextBuilder sslBuilder = SSLContexts.custom();
			// Building the SSLContext usiong the build() method
			SSLContext sslcontext = sslBuilder.build();
			// Creating SSLConnectionSocketFactory object
			SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslcontext,
					new NoopHostnameVerifier());
			// Creating HttpClientBuilder
			HttpClientBuilder clientbuilder = HttpClients.custom();
			// Setting the SSLConnectionSocketFactory & Building the CloseableHttpClient
			return clientbuilder.setSSLSocketFactory(sslConSocFactory).setDefaultRequestConfig(config).build();
		} catch (Exception e) {
			throw new UnhandledServiceException(e.getMessage());
		}

	}

	@Override
	public JSONObject getReq(@NonNull String apiPath, @NonNull MultiValueMap<String, String> queryString) {

		CloseableHttpClient webClient = this.createClient();

		try {

			HttpClientContext httpContext = new HttpClientContext();
			HttpGet getReq = new HttpGet(
					apiPath + UriComponentsBuilder.newInstance().queryParams(queryString).build().encode().toUri());

			HttpResponse resp = webClient.execute(getReq, httpContext);
			JSONParser parser = new JSONParser();

			if (resp.getStatusLine().getStatusCode() != 200) {
				throw new UnhandledServiceException();
			}

			return (JSONObject) parseJsonResp.apply(parser, parseEntityUtil.apply(resp));

		} catch (Exception e) {
			throw new UnhandledServiceException(e.getMessage());
		} finally {

			try {
				webClient.close();
			} catch (IOException e) {
				throw new UnhandledServiceException(e.getMessage());
			}
		}
	}

}
