package com.mysite.used_market.item;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.used_market.inquiry.InquiryForm;
import com.mysite.used_market.user.SiteUser;
import com.mysite.used_market.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/item")
@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;
    
	/*@RequestMapping("/list") // 검색
	public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
		Page<Item> paging = this.itemService.getList(page);
		model.addAttribute("paging", paging);
		return "item_list";
	}*/
	
	// 제목 누르면 내용(상세페이지)로 넘기기
	@RequestMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, InquiryForm inquiryForm) {
		Item item = this.itemService.getItem(id);
		model.addAttribute("item", item);
		return "item_detail";
	}
	
	
    @RequestMapping("/list") // 페이지
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") Integer page,
    		           @RequestParam(value="kw", defaultValue="") String kw) {
        Page<Item> paging = this.itemService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "item_list";
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")    
    public String itemCreate(ItemForm itemForm) {
    	return "item_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String itemCreate(@Valid ItemForm itemForm, BindingResult bindingResult,
    		                     Principal prinipal ) {
        if (bindingResult.hasErrors()) {
            return "item_form";
        }
        SiteUser siteUser = this.userService.getUser(prinipal.getName());
        this.itemService.create(itemForm.getSubject(), itemForm.getContent(), itemForm.getPrice(), siteUser);
        return "redirect:/item/list";
    }
    /*
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String itemModify(ItemForm itemForm, @PathVariable("id") Integer id, Principal principal) {
        Item item = this.itemService.getItem(id);
        if(!item.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        itemForm.setSubject(item.getSubject());
        itemForm.setContent(item.getContent());
        return "item_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String itemModify(@Valid ItemForm itemForm, BindingResult bindingResult, 
            Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "item_form";
        }
        Item item = this.itemService.getItem(id);
        if (!item.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.itemService.modify(item, itemForm.getSubject(), itemForm.getContent());
        return String.format("redirect:/item/detail/%s", id);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Item item = this.itemService.getItem(id);
        if (!item.getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.itemService.delete(item);
        return "redirect:/";
    }*/
    
    
}