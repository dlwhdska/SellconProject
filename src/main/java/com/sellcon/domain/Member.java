package com.sellcon.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Member {
	@Id
	@Column(name="MEMBER_ID", length=20)
	private String id;
	
	@Column(nullable=false, length=100)
	private String pwd;
	
	@Column(nullable=false, length=20)
	private String name;
	
	@Column(nullable=false, length=40)
	private String email;
	
	@Column(nullable=false, length=20)
	private String phone;
	
	@Column(length=20)
	private String bank;
	
	@Column(length=20)
	private String account;

	@Column(columnDefinition="CHAR(1) default 'Y'")
	@Builder.Default
	private String useyn="Y";
	
	@Column(columnDefinition="CHAR(1) default 'N'")
	@Builder.Default
	private String scamyn="N";
	
	@Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regdate;

	

	
}
