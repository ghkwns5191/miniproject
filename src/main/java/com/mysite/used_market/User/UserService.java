package com.mysite.used_market.User;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.used_market.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
   private final UserRepository userRepository;
   
   public SiteUser create(String username, String email, String password,String phonenumber,String address) {
	    SiteUser user = new SiteUser();
	    user.setUsername(username);
	    user.setEmail(email);
	    user.setPhonenumber(phonenumber);
	    user.setAddress(address);
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    user.setPassword(passwordEncoder.encode(password) );
	    this.userRepository.save(user);
	    return user;
   }
   
   public SiteUser getUser(String username) {
	   Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
	   if(siteUser.isPresent()) {
		   return siteUser.get();
	   } else {
		   throw new DataNotFoundException("siteUser not found");
	   }
   }
}
