package com.scs.fis.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.scs.fis.model.entities.FisAccount;
import com.scs.fis.model.entities.FisRole;

public class AuthenticatedAccount extends User {

	private static final long serialVersionUID = 1L;
	private FisAccount user;

	public AuthenticatedAccount(FisAccount user) {
		super(user.getEmail(), user.getPassword(), getAuthorities(user));
		this.user = user;
	}

	public FisAccount getUser() {
		return user;
	}

	private static Collection<? extends GrantedAuthority> getAuthorities(FisAccount user) {
		Set<String> roleAndPermissions = new HashSet<>();
		List<FisRole> roles = user.getRoles();

		for (FisRole role : roles) {
			roleAndPermissions.add(role.getRole());
		}
		String[] roleNames = new String[roleAndPermissions.size()];
		Collection<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(roleAndPermissions.toArray(roleNames));
		return authorities;
	}
}
