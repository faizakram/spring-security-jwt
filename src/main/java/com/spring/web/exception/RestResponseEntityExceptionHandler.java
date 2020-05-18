package com.spring.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.util.CommonConstant;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ErrorCodeHelper errorCodeHelper;

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1000_ERROR_CODE,
				CommonConstant.E1000_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1001_ERROR_CODE,
				CommonConstant.E1001_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1002_ERROR_CODE,
				CommonConstant.E1002_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1003_ERROR_CODE,
				CommonConstant.E1003_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, status, request);
	}

	@ExceptionHandler({ ServiceException.class })
	protected ResponseEntity<Object> handleServiceException(RuntimeException e, WebRequest request) {
		ServiceException exception = (ServiceException) e;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return handleExceptionInternal(exception, exception.getErrorInfo(), headers, exception.getHttpStatus(),
				request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	protected ResponseEntity<Object> emptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1008_ERROR_CODE,
				CommonConstant.E1008_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> notAuthorized(AccessDeniedException ex, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1006_ERROR_CODE,
				CommonConstant.E1006_ERROR_DESCRIPTION);
		return handleExceptionInternal(ex, errorInfo, headers, HttpStatus.UNAUTHORIZED, request);
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleGenericException(RuntimeException e, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpStatus httpStatus = HttpStatus.OK;
		ErrorInfo errorInfo = errorCodeHelper.getErrorInfo(CommonConstant.E1009_ERROR_CODE,
				CommonConstant.E1009_ERROR_DESCRIPTION);
		return handleExceptionInternal(e, errorInfo, headers, httpStatus, request);
	}
}
