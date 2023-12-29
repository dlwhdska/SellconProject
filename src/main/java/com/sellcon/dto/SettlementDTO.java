package com.sellcon.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettlementDTO {

	private Long stseq;
	private Long oseq;
	private String productNames;
	private BigDecimal totalPrice;
	private BigDecimal rate;
	private int settle_amount;
	private String sell_id;
	private String styn;
	private Date orderdate;
	private Date settledate;

}
