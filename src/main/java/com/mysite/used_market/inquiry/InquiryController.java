package com.mysite.used_market.inquiry;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.used_market.item.Item;
import com.mysite.used_market.item.ItemService;
import com.mysite.used_market.user.SiteUser;
import com.mysite.used_market.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inquiry")
public class InquiryController {
	private final ItemService itemService;
	private final InquiryService inquiryService;
	private final UserService userService;

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create/{id}")
	public String createInquiry(Model model, @PathVariable("id") Integer id, @Valid InquiryForm inquiryForm,
			BindingResult bindingResult, Principal principal) {
		Item item = this.itemService.getItem(id);
		SiteUser user = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("Item", item);
			return "item_detail";
		}
		this.inquiryService.createInquiry(item, inquiryForm.getContent(), user);
		return String.format("redirect:/item/detail/%s", id);

	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyInquiry(InquiryForm inquiryForm, @PathVariable("id") Integer id, Principal principal) {
		Inquiry inquiry = this.inquiryService.getInquiry(id);
		if (!inquiry.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "modifying is not authorized");
		}
		inquiryForm.setContent(inquiry.getContent());
		return "inquiry_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyInquiry(@Valid InquiryForm inquiryForm, BindingResult bindingResult,
			@PathVariable("id") Integer id, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "inquiry_form";
		}
		Inquiry inquiry = this.inquiryService.getInquiry(id);
		if (!inquiry.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "modifying is not authorized");
		}
		this.inquiryService.modifyInquiry(inquiry, inquiryForm.getContent());
		return String.format("redirect:/item/detail/%s", inquiry.getItem().getId());
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteInquiry(@PathVariable("id") Integer id, Principal principal) {
		Inquiry inquiry = this.inquiryService.getInquiry(id);
		if (!inquiry.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "deleting is not authorized");
		}
		this.inquiryService.deleteInquiry(inquiry);
		return String.format("redirect:/item/detail/%s", inquiry.getItem().getId());
	}
}

