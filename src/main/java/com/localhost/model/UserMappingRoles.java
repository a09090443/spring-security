package com.localhost.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_mapping_roles")
public class UserMappingRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	UserMappingRolesPK pk;

	public UserMappingRolesPK getPk() {
		return pk;
	}

	public void setPk(UserMappingRolesPK pk) {
		this.pk = pk;
	}

}
