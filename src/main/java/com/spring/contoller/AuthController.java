/**
 * 
 */
package com.spring.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.JwtAuthenticationRequest;
import com.spring.dto.ResponseJson;
import com.spring.util.CommonConstant;
import com.spring.util.MappingConstant;
import com.spring.util.jwt.JwtUtil;
import com.spring.web.exception.ErrorCodeHelper;
import com.spring.web.exception.ErrorInfo;
import com.spring.web.exception.ServiceException;

@RestController
@RequestMapping(MappingConstant.LOGIN)
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	@Qualifier(value = "JwtUtilWithoutDbCheckImpl")
	private JwtUtil jwtTokenUtil;
	@Autowired
	private ResponseJson responseJson;

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@PostMapping(MappingConstant.TOKEN)
	public ResponseJson createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
		try {
			final Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (AuthenticationException e) {
			ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1005_ERROR_CODE,
					CommonConstant.E1005_ERROR_DESCRIPTION);
			throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
		}
		responseJson.setResponse(jwtTokenUtil.generateToken(authenticationRequest.getUsername()));
		return responseJson;
	}
}
