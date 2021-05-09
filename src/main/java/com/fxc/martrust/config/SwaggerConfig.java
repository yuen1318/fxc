package com.fxc.martrust.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private final String CONTROLLER_PACKAGE = "com.fxc.martrust.controller"; 
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage(CONTROLLER_PACKAGE))
				.paths(regex("/fx-api/.*")).build().apiInfo(this.buildApiInfo());
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title("FX API")
				.description("Foreign Exchange API Definition")
				.version("1.0")
				.license("License of API")
				.licenseUrl("API license URL")
				.build();
	}
}
