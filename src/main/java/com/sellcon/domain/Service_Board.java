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
public class Service_Board {
   @Id
   @GeneratedValue
   @Column(name="qseq", columnDefinition = "number(5)")
   private Long qseq;
   
   @Column(length = 20, nullable = false)
   private String title;
   
   @Column(length = 1000, nullable = false)
   private String content;
   
   @Column(length = 1000, nullable = true)
   private String reply;
   
   @Column(columnDefinition = "char(1) default 'N'", nullable=false)
   private int repyn;
   
   @Column(insertable=false, updatable=false, columnDefinition="DATE default sysdate")
   private Date regdate;
   
   @ManyToOne
   @JoinColumn(name="MEMBER_ID", nullable=true, updatable=false)
   private Member member;
   
}