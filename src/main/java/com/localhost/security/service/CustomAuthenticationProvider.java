package com.localhost.security.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.localhost.model.UserInfo;
import com.localhost.model.UserRole;
import com.localhost.service.IUserService;

@Service("customAuthenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private IUserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();
 
        Object[] userObject = userService.findUserAllInfoByLoginId(loginId);
        
		if (null == userObject) {
			logger.info("User not found : {}", loginId);
			throw new UsernameNotFoundException("");
		}
		
		UserInfo userInfo = (UserInfo) userObject[0];

		if(userInfo.getStatusId().equals("0")){
			logger.info("User is disabled : {}", loginId);
			throw new DisabledException("");
		}
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if(!passwordEncoder.matches(password, userInfo.getPassword())){
			logger.info("Password is wrong : {}", loginId);
			throw new BadCredentialsException("");
		}
		
		List<GrantedAuthority> grantedAuths = new ArrayList<>();

		for (UserRole userRole : (List<UserRole>) userObject[1]) {
	        grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleType()));
		}

        return new UsernamePasswordAuthenticationToken(loginId, password, grantedAuths);
	}

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
