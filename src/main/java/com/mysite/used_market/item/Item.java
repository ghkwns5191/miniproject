package com.mysite.used_market.item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.mysite.used_market.inquiry.Inquiry;
import com.mysite.used_market.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 번호 증가
	private Integer id;
	
	@ManyToOne
	private SiteUser author;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private String price;
	
	private LocalDate createDate;
	
	private LocalDateTime modifyDate;
	
	@OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
	private List<Inquiry> inquiryList;

}