package com.localhost.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "user_info")
@DynamicUpdate(true)
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "user_id", nullable = false, length = 6, unique = true)
	private String userId;

	@NotEmpty
	@Column(name = "login_id", nullable = false, length = 25, unique = true)
	private String loginId;

	@Column(name = "first_name", nullable = false, length = 10)
	private String firstName;

	@Column(name = "last_name", length = 10)
	private String lastName;

	@Column(name = "password", length = 61)
	private String password;

	@Column(name = "status_id", length = 1)
	private String statusId;

	@Column(name = "email", length = 30)
	private String email;

	@Column(name = "image", length = 20)
	private String image;

	@Column(name = "title_id", length = 2)
	private String titleId;

	@Column(name = "birthday")
	private String birthday;

	@Column(name = "address", length = 40)
	private String address;

	@Column(name = "phone", length = 11)
	private String phone;

	@Column(name = "registerTime")
	private String registerTime;

	@Transient
	private String statusName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
