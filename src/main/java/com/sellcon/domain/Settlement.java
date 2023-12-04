package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	@Column(name="settlseq", columnDefinition = "NUMBER(5)")
	@GeneratedValue
	private Long settlseq;
	
	@Column(columnDefinition="CHAR(1) default 'n'")
	private String settlyn;
	
	@Column(insertable=false, updatable=false, columnDefinition="DATE default sysdate")
	private Date settledate;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="oseq")
	private Orders orders;
	
}
