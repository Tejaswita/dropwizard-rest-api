package domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import domain.Role;

public class AuthenticatedUser {

	@JsonProperty
	private String username;
	@JsonProperty
	private Role role;

	public AuthenticatedUser() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return this.username;
	}

	public Role getRole() {
		return this.role;
	}

	public AuthenticatedUser(String username, String role) {
		this.username = username;
		this.role = Role.toRole(role);
	}
}
