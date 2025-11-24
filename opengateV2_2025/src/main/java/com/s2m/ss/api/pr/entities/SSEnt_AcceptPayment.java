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
public class SSEnt_AcceptPayment {

	
	@JsonProperty("requestid")
	private String requestid;
	
	@JsonProperty("transactiondate")
	private String transactiondate;
	
	@JsonProperty("merchantid")
	private String merchantid;
	
	@JsonProperty("entitycode")
	private String entitycode;
	
	@JsonProperty("entitytype")
	private String entitytype;
	
	@JsonProperty("amount")
	private String amount;
	
	@JsonProperty("currencycode")
	private String currencycode;
	
	@JsonProperty("labeltrx")
	private String labeltrx;
	
	@JsonProperty("transctx")
	private String transctx;
	
	@JsonProperty("trxtype")
	private String trxtype;
	

}
