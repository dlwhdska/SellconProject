package com.sellcon.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Admin {
	
	@Id
	@Column(name="ADMIN_ID", length = 20)
	private String id;
	
	@Column(length = 20, nullable = false)
	private String pwd;
	
	@Column(length = 40, nullable = false)
	private String name;
	
	@Column(length = 20, nullable = false)
	private String phone;
}