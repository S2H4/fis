package com.scs.fis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AutomaticLoginService {

	@Autowired
	CustomUserDetailsService userDetailsManager;

	public boolean authenticateUserAndInitializeSessionByUsername(String username, HttpServletRequest request) {
		boolean result = true;

		try {
			// generate session if one doesn't exist
			request.getSession();

			// Authenticate the user
			UserDetails user = userDetailsManager.loadUserByUsername(username);
			Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (Exception e) {
			System.out.println(e.getMessage());

			result = false;
		}

		return result;
	}

}
