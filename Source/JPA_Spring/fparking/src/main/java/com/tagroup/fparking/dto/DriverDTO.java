package com.tagroup.fparking.dto;

public class DriverDTO {

private Long id;
	
	private String name;
	
	private String phone;
	
	private String password;
	
	private String newpassword;
	
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNewpassword() {
		return newpassword;
	}

	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "DriverDTO [id=" + id + ", name=" + name + ", phone=" + phone + ", password=" + password
				+ ", newpassword=" + newpassword + ", status=" + status + "]";
	}
	

}
