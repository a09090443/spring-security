package com.localhost.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.localhost.model.UserInfo;
import com.localhost.model.UserRole;
import com.localhost.service.IUserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private IUserService userService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		Object[] userObject = userService.findUserAllInfoByLoginId(loginId);
		if (null == userObject) {
			throw new UsernameNotFoundException("");
		}

		List<String> roleTypeList = new ArrayList<String>();
		UserInfo userInfo = new UserInfo();
		userInfo = (UserInfo) userObject[0];

		if(userInfo.getStatusId().equals("0")){
			throw new DisabledException("");
		}
		for (UserRole userRole : (List<UserRole>) userObject[1]) {
			roleTypeList.add(userRole.getRoleType());
		}
		return new org.springframework.security.core.userdetails.User(loginId, userInfo.getPassword(), true, true, true,
				true, getGrantedAuthorities(roleTypeList));
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> roleTypeList) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String roleType : roleTypeList) {
			logger.info("UserRule : {}", roleType);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + roleType));
		}
		logger.info("authorities : {}", authorities);
		return authorities;
	}
}
