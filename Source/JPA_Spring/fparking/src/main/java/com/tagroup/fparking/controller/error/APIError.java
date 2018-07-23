package com.tagroup.fparking.controller.error;

import java.io.Serializable;
import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * @author admin
 *
 */
public class APIError implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date timestamp;
	private int status;
	private String error;
	private String message;
	private String path;
	private String method;

	public APIError() {
		this.timestamp = new Date();
	}

	public APIError(HttpStatus status, String message) {
		this();
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	public String toString() {
		return "APIError [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", message=" + message
				+ ", path=" + path + ", method=" + method + "]";
	}

}
