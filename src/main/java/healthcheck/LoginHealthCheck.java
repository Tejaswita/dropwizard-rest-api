package healthcheck;

import javax.ws.rs.WebApplicationException;

import resource.LoginResource;

import com.yammer.metrics.core.HealthCheck;

import dto.AuthenticatedUser;

public class LoginHealthCheck extends HealthCheck {

	private final LoginResource login;

	public LoginHealthCheck(LoginResource loginResource) {
		super("login check");
		this.login = loginResource;
	}

	@Override
	protected Result check() throws Exception {
		try {
			AuthenticatedUser authenticatedUser = login.login("testuser",
					"password");
			if (authenticatedUser.getUsername().equals("testuser")) {
				return Result.healthy("Login up and running");
			}
		} catch (WebApplicationException exception) {
			return Result.unhealthy("Cannot talk to login");
		}
		return Result.unhealthy("Login service not up");
	}
}
