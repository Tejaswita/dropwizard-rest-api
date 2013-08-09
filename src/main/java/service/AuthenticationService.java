package service;

import model.Identity;
import javassist.tools.rmi.ObjectNotFoundException;
import dao.IdentityDao;
import dto.AuthenticatedUser;
import exception.InvalidCredentialsException;

public class AuthenticationService {

	private final IdentityDao identityDao;

	public AuthenticationService(IdentityDao userDao) {
		this.identityDao = userDao;
	}

	public AuthenticatedUser authenticate(String username, String password)
			throws InvalidCredentialsException {
		try {
			Identity identity = identityDao.find(username, password);
			if (identity == null) {
				throw new InvalidCredentialsException("bad credentials");
			}
			return identity.toAuthenticatedUser();
		} catch (ObjectNotFoundException e) {
			throw new InvalidCredentialsException("bad credentials");
		}
	}
}
