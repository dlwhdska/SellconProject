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
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CS {
	@Id
	@GeneratedValue
	@Column(name="csseq", columnDefinition = "number(5)")
	private Long csseq;

	@Column(length = 20)
	private String title;
	
	@Column(length = 1000)
	private String content;
	
	@Column(columnDefinition = "char(1)")
	private int cs_category;
	
	@Column(insertable=false, updatable=false, columnDefinition="DATE default sysdate")
	private Date regdate;
	
	@ManyToOne
	@JoinColumn(name="ADMIN_ID", nullable=true, updatable=false)
	private Admin admin;
}