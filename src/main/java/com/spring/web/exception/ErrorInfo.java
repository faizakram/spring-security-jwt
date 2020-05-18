package com.spring.web.exception;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * ErrorInfo class having information about custom exceptions
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties({ "targetClass", "targetSource", "targetObject", "advisors", "frozen", "exposeProxy",
	"preFiltered", "proxiedInterfaces", "proxyTargetClass" })
public class ErrorInfo implements Serializable {

	private static final long serialVersionUID = 67549136213141491L;

	private String responseCode;

	private String responseDescription;

	private Integer referenceNumber;

	private ErrorMessage errorMessage;

	public Integer getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Integer referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	@Override
	public String toString() {
		return "ErrorInfo [responseCode=" + responseCode + ", responseDescription=" + responseDescription
				+ ", referenceNumber=" + referenceNumber + ", errorMessage=" + errorMessage + "]";
	}

}
