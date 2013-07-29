package service;

import javassist.tools.rmi.ObjectNotFoundException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import dao.IdentityDao;
import domain.Identity;
import domain.Role;
import domain.dto.AuthenticatedUser;
import exception.InvalidCredentialsException;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

	@Mock
	private IdentityDao userDao;

	private AuthenticationService service;
	private String username;
	private String password;

	@Mock
	private Identity identity;

	@Before
	public void setup() {
		username = "username";
		password = "password";
		service = new AuthenticationService(userDao);
	}

	@Test
	public void shouldAuthenticateValidUser() throws Exception {
		// Given
		AuthenticatedUser userToLogin = new AuthenticatedUser(username,
				Role.EMPLOYEE.name());
		given(userDao.find(username, password)).willReturn(identity);
		given(identity.getUsername()).willReturn(username);
		given(identity.getRole()).willReturn(Role.EMPLOYEE.name());
		given(identity.toAuthenticatedUser()).willReturn(userToLogin);

		// When
		AuthenticatedUser authenticatedUser = service.authenticate(username,
				password);

		// Then
		assertThat(authenticatedUser, is(userToLogin));
		verify(identity).toAuthenticatedUser();
	}

	@Test(expected = InvalidCredentialsException.class)
	public void shouldThrowExceptionForNoUserFound() throws Exception {
		// Given
		given(userDao.find(username, password)).willReturn(null);

		// When
		service.authenticate(username, password);
	}

	@Test(expected = InvalidCredentialsException.class)
	public void shouldThrowExceptionForObjectNotFoundException()
			throws Exception {
		// Given
		given(userDao.find(username, password)).willThrow(
				new ObjectNotFoundException("no matching object"));

		// When
		service.authenticate(username, password);

	}
}
