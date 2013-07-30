package resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import service.AuthenticationService;

import com.yammer.dropwizard.hibernate.UnitOfWork;

import domain.dto.AuthenticatedUser;
import exception.InvalidCredentialsException;

@Path(value = "/login")
public class LoginResource {

	private final AuthenticationService authService;

	public LoginResource(AuthenticationService authService) {
		this.authService = authService;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@UnitOfWork
	public AuthenticatedUser login(
			@FormParam(value = "username") String username,
			@FormParam(value = "password") String password) {
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			Response response = Response.status(Response.Status.BAD_REQUEST)
					.build();
			throw new WebApplicationException(response);
		}
		try {
			return authService.authenticate(username, password);
		} catch (InvalidCredentialsException e) {
			Response response = Response.status(Response.Status.BAD_REQUEST)
					.build();
			throw new WebApplicationException(response);
		}
	}

}
