package com.mysite.used_market.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	
	@Size(min=3, max=25)
	  @NotEmpty(message="사용자ID는 필수항목입니다.")
	  private String username;
	  
	  @NotEmpty(message="비밀번호는 필수항목입니다.")
	  private String password1;
	  
	  @NotEmpty(message="비밀번호확인는 필수항목입니다.")
	  private String password2;
	  
	  @NotEmpty(message="이메일은 필수항목입니다.")
	  @Email
	  private String email;
	  
	  @NotEmpty(message="전화번호는 필수항목입니다.")
	  private String phonenumber;
	  
	  @NotEmpty(message="주소입력은 필수항목입니다.")
	  private String address;
	  
}
