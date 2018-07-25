package com.tagroup.fparking.security;

import java.io.Serializable;

public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private Long id;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", id=" + id + "]";
	}

}
