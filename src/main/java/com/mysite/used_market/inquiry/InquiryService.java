package com.mysite.used_market.inquiry;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.used_market.DataNotFoundException;
import com.mysite.used_market.item.Item;
import com.mysite.used_market.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InquiryService {

	private final InquiryRepository inquiryRepository;

	public Inquiry getInquiry(Integer id) {
		Optional<Inquiry> inquiry = this.inquiryRepository.findById(id);
		if (inquiry.isPresent()) {
			return inquiry.get();
		} else {
			throw new DataNotFoundException("Inquiry not found");
		}
	}

	public void createInquiry(Item item, String content, SiteUser author) {
		Inquiry inquiry = new Inquiry();
		inquiry.setContent(content);
		inquiry.setCreateDate(LocalDateTime.now());
		inquiry.setItem(item);
		inquiry.setAuthor(author);
		this.inquiryRepository.save(inquiry);
	}

	public void modifyInquiry(Inquiry inquiry, String content) {
		inquiry.setContent(content);
		inquiry.setModifyDate(LocalDateTime.now());
		this.inquiryRepository.save(inquiry);
	}

	public void deleteInquiry(Inquiry inquiry) {
		this.inquiryRepository.delete(inquiry);
	}

//	public void vote(Inquiry inquiry, SiteUser user) {
//		inquiry.getVoter().add(user);
//		this.inquiryRepository.save(inquiry);
//	}
	
}
