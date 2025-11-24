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
public class SSEnt_AnonymDebitCard {
	
	@JsonProperty("bankcode")
	private String  bankcode ;
	@JsonProperty("currencyid")
	private String  currencyid ;
	@JsonProperty("cardprogramcode")
	private String  cardprogramcode ;
	@JsonProperty("debitprogramcode")
	private String  debitprogramcode ;
	@JsonProperty("quantity")
	private String  quantity;
	@JsonProperty("branchcode")
	private String  branchcode ;
	@JsonProperty("cardcategory")
	private String  cardcategory ;
	@JsonProperty("target_audience")
	private String  target_audience ;
	@JsonProperty("initamount")
	private String  initamount ;
	
	
  
}
