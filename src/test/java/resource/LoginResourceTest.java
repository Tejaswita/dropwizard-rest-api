package resource;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import service.AuthenticationService;
import domain.dto.AuthenticatedUser;

@RunWith(MockitoJUnitRunner.class)
public class LoginResourceTest {

	@Mock
	private AuthenticationService authService;
	private LoginResource loginResource;

	@Before
	public void setup() {
		loginResource = new LoginResource(authService);
	}

	@Test
	public void shouldLoginValidUser() throws Exception {
		// Given
		String username = "username";
		String password = "password";
		AuthenticatedUser user = new AuthenticatedUser(username, "Employee");

		given(authService.authenticate(username, password)).willReturn(user);

		// When
		AuthenticatedUser loggedInUser = loginResource
				.login(username, password);

		// Then
		assertThat(loggedInUser, is(user));
	}

	@Test
	public void shouldSendBadRequestAsResponseIfUsernameIsBlank()
			throws Exception {
		// Given
		String username = "";
		String password = "password";
		AuthenticatedUser user = new AuthenticatedUser(username, "Employee");

		given(authService.authenticate(username, password)).willReturn(user);

		// When
		try {
			loginResource.login(username, password);
		} catch (WebApplicationException webException) {
			assertThat(webException.getResponse().getStatus(), is(400));
		}

	}
}
