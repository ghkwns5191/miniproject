package com.mysite.used_market.item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.used_market.DataNotFoundException;
import com.mysite.used_market.used_user.SiteUser;

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
		
		public void create(String subject, String content, SiteUser user) {
			Item i = new Item();
			i.setSubject(subject);
			i.setContent(content);
			i.setCreateDate(LocalDate.now());
			this.itemRepository.save(i);
		}

		public Page<Item> getList(int page) {
			// TODO Auto-generated method stub
			return null;
		}


}
