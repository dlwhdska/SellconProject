package com.sellcon.domain;

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
public class Brand {
	
	@Id
	@GeneratedValue
	@Column(columnDefinition = "number(5)")
	private Long bseq;
	
	@Column(length = 40, nullable = false)
	private String brand_name;
	
	@Column(length = 100, nullable = false)
	private String brand_image;
	
	@ManyToOne
	@JoinColumn(name="kind", updatable=false)
	private Category category;
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
