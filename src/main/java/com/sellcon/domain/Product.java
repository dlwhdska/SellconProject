package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Product {

	@Id
	@GeneratedValue
	@Column(name = "pseq", columnDefinition = "NUMBER(5)")
	private Long pseq;

	@Column(name = "product_name", length = 100, nullable = false)
	private String productName;

	@Column(columnDefinition = "number(7)", nullable = false)
	private int price;

	@Column(length = 1000, nullable = false)
	private String content;

	@Column(columnDefinition = "char(1) default 'Y'", nullable = false)
	private char useyn;

	@Column(length = 100, nullable = false)
	private String image;

	@Column(insertable = false, updatable = false, columnDefinition = "DATE default sysdate")
	private Date regdate;

	@ManyToOne
	@JoinColumn(name = "bseq", updatable = false)
	private Brand brand;

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

}