package com.mysite.used_market.inquiry;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryForm {

	@NotEmpty(message = "내용은 필수입니다. 내용을 입력하세요!")
	private String content;
}
// 