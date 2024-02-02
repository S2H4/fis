package com.scs.fis.staticpages.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.scs.fis.model.entities.FisAccount;
import com.scs.fis.security.CustomUserDetailsService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CoController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@GetMapping("/co/home")
	public String getCoHomePage(Model model) {
		try {
			FisAccount currentUser = customUserDetailsService.getCurrentLoggedInUser();
			model.addAttribute("currentLoggedInUserEmail", currentUser.getEmail());
		} catch (Exception ex) {
			log.error("Error: {}", ex.getMessage());
		}
		return "co/fis_home";
	}
}
