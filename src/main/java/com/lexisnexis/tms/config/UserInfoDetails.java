//package com.lexisnexis.tms.config;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.lexisnexis.tms.entity.UserAdmin;
//import com.lexisnexis.tms.repository.UserAdminRepo;
//
////@Component
////public class UserInfoDetails implements UserDetailsService {
////
////	@Autowired
////	UserAdminRepo userAdminRepo;
////	
////	@Override
////	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////		Optional<UserAdmin> userinfo= userAdminRepo.findByName(username);
////		userinfo.map(UserInfoUserDetails::new)
////		.orElseThrow(()->new UsernameNotFoundException("user not found "+username));
////		return null;
////	}
////
////}
