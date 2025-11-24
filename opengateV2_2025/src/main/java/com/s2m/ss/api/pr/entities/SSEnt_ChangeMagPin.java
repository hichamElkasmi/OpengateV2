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
@ToString 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SSEnt_ChangeMagPin {	
	@JsonProperty("institution")
	private String institution;
	@JsonProperty("pan_number")
	private String pan_number;
	@JsonProperty("new_pin")
	private String new_pin;
	@JsonProperty("old_pin")
	private String old_pin;
}
