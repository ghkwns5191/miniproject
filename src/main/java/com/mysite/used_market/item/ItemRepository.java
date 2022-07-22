package com.mysite.used_market.item;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
	Item findBySubject(String subject);
    Item findBySubjectAndContent(String subject, String content);
    List<Item> findBySubjectLike(String subject);
    Page<Item> findAll(Specification<Item> spec, Pageable pageable);
    // Page<Item> findAll(org.springframework.data.jpa.domain.Specification<Item> spec, Pageable pageable);
	
}