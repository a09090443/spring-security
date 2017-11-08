package com.localhost.security.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class LoginFailureHandler implements AuthenticationFailureHandler {
	private static final Logger log = LoggerFactory.getLogger(LoginFailureHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String address = request.getRemoteAddr();
		
		log.info("Source ip address : {}", address);
		
		if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
			response.sendRedirect("login?auth=fail&error=user.not.found");
		} else if (exception.getClass().isAssignableFrom(DisabledException.class)) {
			response.sendRedirect("login?auth=fail&error=user.disabled");
		} else if (exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
			response.sendRedirect("login?auth=fail&error=bad.credential");
		} else {
			response.sendRedirect("login?auth=fail&error=error");
		}
	}

}
