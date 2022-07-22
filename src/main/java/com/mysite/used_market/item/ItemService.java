package com.mysite.used_market.item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.used_market.DataNotFoundException;
import com.mysite.used_market.inquiry.Inquiry;
import com.mysite.used_market.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {

private final ItemRepository itemRepository;

	
	/* public Page<Item> getList(int page, String kw){
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable= PageRequest.of(page, 10);
		Specification<Item> spec = search(kw);
		return this.itemRepository.findAllByKeyword(kw, pageable);
	} */

		public Item getItem(Integer id) {
			Optional<Item> item = this.itemRepository.findById(id);
			if(item.isPresent()) {
				return item.get();
			} else {
				throw new DataNotFoundException("item not found");
		
			}
		}
		
		public void create(String subject, String content, String price, SiteUser author) {
			Item i = new Item();
			i.setSubject(subject);
			i.setContent(content);
			i.setPrice(price);
			i.setAuthor(author);
			i.setCreateDate(LocalDate.now());
			this.itemRepository.save(i);
		}

		public Page<Item> getList(int page, String kw) {
			List<Sort.Order> sorts = new ArrayList<>();
			sorts.add(Sort.Order.desc("createDate"));
			Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
			Specification<Item> spec = search(kw);
			return this.itemRepository.findAll(spec, pageable);
		}
		public void modify(Item item, String subject, String content, String price) {
			item.setSubject(subject);
			item.setContent(content);
			item.setPrice(price);
			item.setModifyDate(LocalDateTime.now());
			this.itemRepository.save(item);
		}
		
	    public void delete(Item item) {
	        this.itemRepository.delete(item);
	    }
	   
//	    public void vote(Item item, SiteUser siteUser) {
//	        item.getVoter().add(siteUser);
//	        this.itemRepository.save(item);
//	    }
		    
		    
	    private Specification<Item> search(String kw) {
	        return new Specification<>() {
	            private static final long serialVersionUID = 1L;
	            @Override
	            public Predicate toPredicate(Root<Item> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                query.distinct(true);  // 중복을 제거 
	                Join<Item, SiteUser> u1 = q.join("author", JoinType.LEFT);
	                Join<Item, Inquiry> a = q.join("inquiryList", JoinType.LEFT);
	                Join<Inquiry, SiteUser> u2 = a.join("author", JoinType.LEFT);
	                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목 
	                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용 
	                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자 
	                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용 
	                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자 
	            }
	        };
	    
				
				
		}
	}