package com.sellcon.domain;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Settlement {
	@Id
	@Column(name="stseq", length=5)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long stseq;
	
	@Column(precision = 3, scale = 2)
	private BigDecimal rate;
	
	@Column(length = 7)
	private int settle_amount;
	
	@Column(length = 20)
	private String sell_id;
	
	@Column(columnDefinition="CHAR(1) default 'N'")
	@Builder.Default
	private String styn="N";
	
	@Column(columnDefinition="DATE")
	private Date settledate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="oseq")
	private Orders orders;
	
}
