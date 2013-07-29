package authentication;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import resource.LoginResource;
import service.AuthenticationService;

import com.sun.jersey.api.representation.Form;
import com.sun.jersey.server.impl.template.ViewableMessageBodyWriter;
import com.yammer.dropwizard.testing.ResourceTest;

import domain.Role;
import domain.dto.AuthenticatedUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginAcceptanceTest extends ResourceTest {

	// @Rule
	// public DropwizardServiceRule<BasicRestApiConfig> server = new
	// DropwizardServiceRule<BasicRestApiConfig>(
	// RestApiService.class, this.getClass()
	// .getResource("test-rest-api.yml").getPath());

	@Mock
	private AuthenticationService authenticationService;
	private Form form;
	private String username;
	private String password;

	@Override
	protected void setUpResources() throws Exception {
		username = "aa";
		password = "bb";
		AuthenticatedUser user = new AuthenticatedUser(username,
				Role.EMPLOYEE.name());
		form = new Form();
		form.add("username", username);
		form.add("password", password);

		given(authenticationService.authenticate(username, password))
				.willReturn(user);
		addResource(new LoginResource(authenticationService));
		addProvider(ViewableMessageBodyWriter.class);
	}

	@Test
	public void shouldAuthenticateUser() throws Exception {
		// Given
		AuthenticatedUser authenticatedUser = client().resource("/login")
				.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(AuthenticatedUser.class, form);
		// Then
		assertThat(authenticatedUser.getUsername(), is(username));
		assertThat(authenticatedUser.getRole(), is(Role.EMPLOYEE));
	}
}
