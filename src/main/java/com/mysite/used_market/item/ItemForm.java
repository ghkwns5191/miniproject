package com.mysite.used_market.item;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemForm {
	@NotEmpty(message="제목은 필수 입니다")
	@Size(max=200)
	private String subject;
	
	@NotEmpty(message="내용은 필수 입니다.")
	private String content;
}
