package dao;

import javassist.tools.rmi.ObjectNotFoundException;

import model.Identity;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.yammer.dropwizard.hibernate.AbstractDAO;

import exception.InvalidCredentialsException;

public class IdentityDao extends AbstractDAO<Identity> {

	public IdentityDao(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Identity find(String username, String password)
			throws InvalidCredentialsException, ObjectNotFoundException {
		Criteria userCriteria = currentSession().createCriteria(Identity.class);
		userCriteria.add(Restrictions.eq("username", username));
		userCriteria.add(Restrictions.eq("password", password));

		Identity identity = (Identity) userCriteria.uniqueResult();
		if (identity == null) {
			throw new ObjectNotFoundException("no matching object");
		}
		return identity;
	}
}
