package com.fxc.martrust.fx_rate_service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class SellReqDto {
	
	@NotNull(message = "must not be null")
	private Integer stake;
	
	@NotEmpty(message = "must not be empty")
	private String rollingSpot;
	
	private Double orderLevel;
	
}
