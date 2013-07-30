package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import domain.dto.AuthenticatedUser;

@Entity
@Table(name = "identity")
public class Identity {

	@Id
	private String username;
	private String password;
	private String role;
	private String firstname;
	private String lastname;

	public Identity() {
	}

	public String getUsername() {
		return this.username;
	}

	public String getRole() {
		return this.role;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public AuthenticatedUser toAuthenticatedUser() {
		AuthenticatedUser user = new AuthenticatedUser(this.username, this.role);
		return user;
	}

	public String getPassword() {
		return password;
	}

}
