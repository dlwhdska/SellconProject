package com.sellcon.dto;

import java.math.BigDecimal;
import java.util.Date;

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
public class MySettlementDTO {
	
	private Long oseq;
    private Long sseq;
    private String productNames;
    private BigDecimal totalPrice;
    private BigDecimal rate;
    private BigDecimal settle_amount;
    private String styn;
    private Date orderdate;
    private Date settledate;

}
