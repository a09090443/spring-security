package com.localhost.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserMappingRolesPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "login_id", nullable = false, length = 6)
	private String loginId;

	@Column(name = "role_id", nullable = false, length = 2)
	private String roleId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
