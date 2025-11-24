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
public class SSEnt_UpdateRiskCardNew {
	@JsonProperty("risk_code")
	private String risk_code;
	@JsonProperty("periodicity_id")
	private String periodicity_id;
	@JsonProperty("periodicity_code")
	private String periodicity_code;
	@JsonProperty("transaction_type")
	private String transaction_type;
	@JsonProperty("transaction_mode")
	private String transaction_mode;
	@JsonProperty("tans_max")
	private String tans_max;
	@JsonProperty("mnt_limite")
	private String mnt_limite;
	@JsonProperty("limite_number")
	private String limite_number;
	@JsonProperty("internet")
	private String internet;
	@JsonProperty("domain_type")
	private String domain_type;
	@JsonProperty("Status")
	private String Status;

}
