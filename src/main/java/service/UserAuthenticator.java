package service;

import dao.UserDao;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.java8.auth.Authenticator;
import model.User;

import java.util.Optional;

public class UserAuthenticator implements Authenticator<BasicCredentials, String> {

    private UserDao dao;

    public UserAuthenticator(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<String> authenticate(BasicCredentials credentials) throws AuthenticationException {
        User user = dao.getUser(credentials.getUsername());
        if(user != null && matches(credentials, user)) {
            return Optional.of(user.getUsername());
        }
        return Optional.empty();
    }

    private boolean matches(BasicCredentials credentials, User user) {
        return user.getPassword().equals(credentials.getPassword());
    }
}
