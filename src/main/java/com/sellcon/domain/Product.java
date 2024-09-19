package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pseq", length=5)
	private Long pseq;

	@Column(name = "product_name", length = 100, nullable = false)
	private String productName;

	@Column(length=7, nullable = false)
	private Integer price;

	@Column(length = 1000, nullable = false)
	private String content;

	@Column(columnDefinition = "char(1) default 'Y'", nullable = false)
	private char useyn;

	@Column(length = 100, nullable = false)
	private String image;

	@Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regdate;

	@ManyToOne
	@JoinColumn(name = "bseq", updatable = false)
	private Brand brand;

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

}
