package com.spring.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.spring.util.CommonConstant;
import com.spring.web.exception.ErrorCodeHelper;
import com.spring.web.exception.ErrorInfo;
import com.spring.web.exception.ServiceException;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1005_ERROR_CODE,
				CommonConstant.E1005_ERROR_DESCRIPTION);
		throw new ServiceException(errorInfo, HttpStatus.UNAUTHORIZED);
	}
}