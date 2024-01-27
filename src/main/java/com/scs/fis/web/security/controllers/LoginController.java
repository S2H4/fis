package com.scs.fis.web.security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	@GetMapping(value = "/login")
	public String loginPage(HttpServletRequest request, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		String referrer = request.getHeader("Referer");

		if (referrer != null) {
			request.getSession().setAttribute("url_prior_login", referrer);
		}

		String errorMessage = null;
		if (error != null)
			errorMessage = "Username or Password is incorrect !!";

		if (logout != null)
			errorMessage = "You have been successfully logged out !!";

		if (request.getSession().getAttribute("displayErrorMessage") != null)
			errorMessage = request.getSession().getAttribute("displayErrorMessage").toString();

		model.addAttribute("errorMessage", errorMessage);

		if (request.getSession().getAttribute("displayMessage") != null)
			model.addAttribute("displayMessage", request.getSession().getAttribute("displayMessage").toString());

		request.getSession().removeAttribute("displayMessage");
		request.getSession().removeAttribute("displayErrorMessage");

		return "fis_authentication/fis_login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return "redirect:/login?logout=true";
	}

	@GetMapping(value = "/error")
	public String errorPage() {
		return "error";
	}
}
