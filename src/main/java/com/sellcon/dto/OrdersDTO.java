package com.sellcon.dto;

import java.util.*;

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
public class OrdersDTO {

	private Long cseq;
	private Long sseq;
	private String member_id;
	private String product_name;
	private String image;
	private int price;
	private int selling_price;
	private Date valid;
	private String sell_id;
	
}
