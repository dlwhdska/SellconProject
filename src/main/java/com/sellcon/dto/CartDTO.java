package com.sellcon.dto;

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
public class CartDTO {

	 private Long oseq;
	 private String member_id;
	 private Long sseq;
	 private String image;
	 private String product_name;
	 private int price;
	 private int selling_price;
	 private Date valid;
	 private String result;
	 
}
