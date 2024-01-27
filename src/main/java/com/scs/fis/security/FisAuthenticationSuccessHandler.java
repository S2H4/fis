package com.scs.fis.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scs.fis.model.entities.FisAccount;
import com.scs.fis.model.repos.FisAccountRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class FisAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Autowired
	private FisAccountRepository fisAccountRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(request, authentication);

		if (response.isCommitted()) {
//			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, Authentication authentication) {
		boolean isAdmin = false;
		boolean isCo = false;
		boolean isJco = false;

		Optional<FisAccount> currentLoggedInUser = fisAccountRepository.findByEmail(authentication.getName());

		if (!currentLoggedInUser.isPresent())
			return "/error";

//		if (!currentLoggedInUser.get().isEnabled()) {
//			return "/error/verify-email";
//		}

//		if (!currentLoggedInUser.get().isMobileVerified()) {
//			return "/error/verify-mobile";
//		}

//		if (!currentLoggedInUser.get().getApproved()) {
//			return "/error/approval-needed";
//		}

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_CO")) {
				isCo = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_JCO")) {
				isJco = true;
				break;
			}
		}

//		HttpSession session = request.getSession();

		if (isAdmin) {
//			if (session != null && !session.getAttribute("url_prior_login").toString().contains("/login")
//					&& !session.getAttribute("url_prior_login").toString().contains("/manager")
//					&& !session.getAttribute("url_prior_login").toString().contains("/buyer")
//					&& !session.getAttribute("url_prior_login").toString().equals(BASE_URL)
//					&& !session.getAttribute("url_prior_login").toString().equals("/registration")) {
//				return session.getAttribute("url_prior_login").toString();
//			} else {
//				return "/admin/home";
//			}
			return "/admin/home";
		} else if (isCo) {
//			if (session != null && !session.getAttribute("url_prior_login").toString().contains("/login")
//					&& !session.getAttribute("url_prior_login").toString().contains("/manager")
//					&& !session.getAttribute("url_prior_login").toString().contains("/admin")
//					&& !session.getAttribute("url_prior_login").toString().equals(BASE_URL)
//					&& !session.getAttribute("url_prior_login").toString().equals("/registration")) {
//				return session.getAttribute("url_prior_login").toString();
//			} else {
//				return "/buyer/home";
//			}
			return "/co/home";
		} else if (isJco) {
//			if (session != null && !session.getAttribute("url_prior_login").toString().contains("/login")
//					&& !session.getAttribute("url_prior_login").toString().contains("/buyer")
//					&& !session.getAttribute("url_prior_login").toString().contains("/admin")
//					&& !session.getAttribute("url_prior_login").toString().equals(BASE_URL)
//					&& !session.getAttribute("url_prior_login").toString().equals("/registration")) {
//				return session.getAttribute("url_prior_login").toString();
//			} else {
//				return "/manager/home";
//			}
			return "/jco/home";
		} else {
			return "/error";
		}

	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
