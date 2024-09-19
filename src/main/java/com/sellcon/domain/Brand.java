package com.sellcon.domain;

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
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="bseq", length=5)
	private Long bseq;
	
	@Column(name="brand_name", length = 40, nullable = false)
	private String brandName;
	
	@Column(name="brand_image", length = 100, nullable = false)
	private String brandImage;
	
	@ManyToOne
	@JoinColumn(name="kind", updatable=false)
	private Category category;
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
