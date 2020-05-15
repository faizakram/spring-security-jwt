package com.spring.util.jwt;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.spring.dto.JwtAuthenticationResponse;

import io.jsonwebtoken.Claims;

public interface JwtUtil {

	public String getIdFromToken(String token);

	public Long getUserIdFromToken(String token);

	public String getUsernameFromToken(String token);

	public List<SimpleGrantedAuthority> getRolesFromToken(String token);

	public Date getCreatedDateFromToken(String token);

	public Date getExpirationDateFromToken(String token);

	public Claims getClaimsFromToken(String token);

	public Boolean isTokenExpired(String token);

	public Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset);

	public JwtAuthenticationResponse generateToken(String username);

	public JwtAuthenticationResponse refreshToken(String token);

	public Boolean validateToken(String token);

}
