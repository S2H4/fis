package com.scs.fis.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.scs.fis.security.FisAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = "com.scs.fis")
public class WebSecurityConfig {

//	@Autowired
//	private UserDetailsService customUserDetailsService;

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}

	@SuppressWarnings({ "deprecation", "removal" })
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin().and().authorizeRequests()
				.requestMatchers("/resources/**", "/webjars/**", "/assets/**", "/h2-console").permitAll()
				.requestMatchers("/", "/registration", "/contactus", "/aboutus", "/error/**", "/custom-message/**",
						"/email/**", "/drop/email")
				.permitAll().and().csrf().disable();
		http.headers().frameOptions().sameOrigin().and().authorizeRequests().requestMatchers("/co/**").hasRole("CO")
				.requestMatchers("/jco/**").hasRole("JCO").requestMatchers("/admin/**").hasRole("ADMIN").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").successHandler(fisAuthenticationSuccessHandler())
				.failureUrl("/login?error").permitAll().and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
				.deleteCookies("my-remember-me-cookie").permitAll().and().rememberMe()
				// .key("my-secure-key")
				.rememberMeCookieName("my-remember-me-cookie").tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(24 * 60 * 60).and().exceptionHandling();

		http.headers().frameOptions().sameOrigin();

		return http.build();
	}

	PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
		tokenRepositoryImpl.setDataSource(dataSource);
		return tokenRepositoryImpl;
	}

	@Bean
	public AuthenticationSuccessHandler fisAuthenticationSuccessHandler() {
		return new FisAuthenticationSuccessHandler();
	}
}