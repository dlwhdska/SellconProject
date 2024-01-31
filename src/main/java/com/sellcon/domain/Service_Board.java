package com.sellcon.domain;

import java.util.Date;
import java.util.Optional;

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
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Service_Board {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="qseq", length=5)
   private Long qseq;
   
   @Column(length = 20, nullable = true)
   private String title;
   
   @Column(length = 1000, nullable = true)
   private String content;
   
   @Column(length = 1000, nullable = true)
   private String reply;
   
   @Column(columnDefinition="CHAR(1) default 'N'")
   @Builder.Default
   private String repyn="N";
   
   @Column(insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regdate;
   
   @Column(columnDefinition="CHAR(1)")
   private String category;
   
   @ManyToOne
   @JoinColumn(name="MEMBER_ID", nullable=true, updatable=true)
   private Member member;
   
}