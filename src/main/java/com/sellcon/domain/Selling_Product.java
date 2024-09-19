package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Selling_Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="sseq", length=5)
	private Long sseq;
	
	@Column(columnDefinition = "number(7)", nullable=false)
	private int sellingprice;
	
	@Column(columnDefinition = "number(20)", nullable=false)
	private Long barcode;
	
	@Column(length = 1000)
	private String barcode_image;
	
	@Temporal(TemporalType.DATE)
	private Date valid;

	@Column(insertable=false, updatable=false, columnDefinition="DATE default sysdate")
	private Date regdate;
	
	@Column(columnDefinition = "char(1)")
	private String checkp;
	
	@ManyToOne
	@JoinColumn(name="Sell_ID", updatable=true)
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="pseq", updatable=true)
	private Product product;
	
	// 할인률 계산
	@Transient
	private int discount;
	
    public int getDiscount() {
        long price = this.getProduct().getPrice();
        long sellingPrice = this.getSellingprice();
        double sproductDiscount = ((double) (price - sellingPrice) / price) * 100;
        return (int) Math.floor(sproductDiscount);
    }
    
}