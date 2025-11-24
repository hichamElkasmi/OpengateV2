package com.s2m.ss.api.pr.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SSEnt_UnLinkAccountToCard {
	
	
	@JsonProperty("card")
	private String card;
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("account")
	private String account;
	@JsonProperty("expiry")
	private String expiry;
}
