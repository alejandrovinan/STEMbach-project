package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.User;

import javax.validation.constraints.NotNull;

public class LoginParamsDto {
	
	private String email;
	private String password;
	private User.RoleType roleType;
	
	public LoginParamsDto() {}

	@NotNull
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim();
	}

	@NotNull
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	public User.RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(User.RoleType roleType) {
		this.roleType = roleType;
	}
}
