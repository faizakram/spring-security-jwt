package com.spring.util.jwt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.spring.model.Role;
import com.spring.model.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getUsername(), user.getCredential(),
				mapToGrantedAuthorities(new ArrayList<Role>(user.getRoles())), user.isEnabled());
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> authorities) {
		return authorities.stream()
				.map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getName().toUpperCase()))
				.collect(Collectors.toList());
	}
}
