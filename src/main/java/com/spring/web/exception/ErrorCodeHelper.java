package com.spring.web.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.spring.util.CommonConstant;

/**
 * To provide http error code and regarding description
 *
 */

@Component
@PropertySource({ CommonConstant.ERROR_PROPERTIES })
public class ErrorCodeHelper {

	@Autowired
	private ErrorInfo errorInfo;

	@Autowired
	private Environment errorPropertyReader;

	/**
	 * Get Error info and set error code and error description
	 * 
	 * @param httpErrorCode
	 * @param httpErrorDescription
	 * @return ErrorInfo
	 */
	public ErrorInfo getErrorInfo(String httpErrorCode, String httpErrorDescription) {
		String responseCode = errorPropertyReader.getProperty(httpErrorCode);
		String responseDescription = errorPropertyReader.getProperty(httpErrorDescription);
		errorInfo.setResponseCode(responseCode);
		errorInfo.setResponseDescription(responseDescription);
		return errorInfo;
	}

	/**
	 * Get Error info and set error code, error description and referenceNumber
	 * 
	 * @param httpErrorCode
	 * @param httpErrorDescription
	 * @param referenceNumber
	 * @return ErrorInfo
	 */
	public ErrorInfo getErrorInfo(String httpErrorCode, String httpErrorDescription, Integer referenceNumber) {
		String responseCode = errorPropertyReader.getProperty(httpErrorCode);
		String responseDescription = errorPropertyReader.getProperty(httpErrorDescription);
		errorInfo.setResponseCode(responseCode);
		errorInfo.setResponseDescription(responseDescription);
		errorInfo.setReferenceNumber(referenceNumber);
		return errorInfo;
	}

	/**
	 * This method can be used, if you want to show the specific field as well in
	 * the response description.
	 * 
	 * @param httpErrorCode
	 * @param httpErrorDescription
	 * @param field
	 * @return ErrorInfo
	 */
	public ErrorInfo getErrorInfo(String httpErrorCode, String httpErrorDescription, String field) {
		String responseCode = errorPropertyReader.getProperty(httpErrorCode);
		String responseDescription = errorPropertyReader.getProperty(httpErrorDescription, field);
		errorInfo.setResponseCode(responseCode);
		errorInfo.setResponseDescription(responseDescription);
		return errorInfo;
	}

	public ErrorInfo getError(String httpError, String httpErrorDescription) {
		errorInfo.setResponseCode(httpError);
		errorInfo.setResponseDescription(httpErrorDescription);
		return errorInfo;
	}

	public ErrorInfo getError(String httpError, ErrorMessage errorMessage) {
		errorInfo.setResponseCode(httpError);
		errorInfo.setErrorMessage(errorMessage);
		return errorInfo;
	}
}
