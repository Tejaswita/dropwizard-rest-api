package service;


import dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticatorTest {

    @Mock
    private UserDao userDao;
    private UserAuthenticator authenticator;

    @Before
    public void setup(){
        authenticator = new UserAuthenticator(userDao);
    }

    @Test
    public void itShouldReturnUserForValidCredentials() throws AuthenticationException {
        String username = "user";
        String password = "password";
        BasicCredentials credentials = new BasicCredentials(username, password);
        User user = new User(username, password);

        given(userDao.getUser(username)).willReturn(user);

        Optional<String> actualUser = authenticator.authenticate(credentials);

        assertThat("Authenticated user does not match", actualUser.get(), is(username));
    }

    @Test
    public void itShouldReturnEmptyUserForInvalidCredentials() throws AuthenticationException {
        String username = "user";
        String password = "password";
        BasicCredentials credentials = new BasicCredentials(username, password);
        User user = new User(username, "somethingelse");

        given(userDao.getUser(username)).willReturn(user);

        assertThat(authenticator.authenticate(credentials).isPresent(), is(false));
    }
}