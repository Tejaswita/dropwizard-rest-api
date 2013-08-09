package dao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import javassist.tools.rmi.ObjectNotFoundException;

import model.Identity;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import exception.InvalidCredentialsException;

@RunWith(MockitoJUnitRunner.class)
public class IdentityDaoTest {

	@Mock
	private SessionFactory sessionFactory;
	@Mock
	private Session session;

	@Mock
	private Criteria criteria;
	@Mock
	private Identity identity;

	@Test
	public void shouldThrowInvalidCredentialExceptionIfNoMatchingUserFound()
			throws Exception {
		// Given

		ArgumentCaptor<SimpleExpression> criteriaArgument = ArgumentCaptor
				.forClass(SimpleExpression.class);
		IdentityDao dao = new IdentityDao(sessionFactory);
		given(sessionFactory.getCurrentSession()).willReturn(session);
		given(session.createCriteria(Identity.class)).willReturn(criteria);
		given(criteria.add((Criterion) anyObject())).willReturn(criteria);

		// When
		try {
			dao.find("user", "password");
		} catch (ObjectNotFoundException exception) {
			assertThat(exception.getMessage(),
					containsString("no matching object"));

		}
		// Then
		verify(criteria, times(2)).add(criteriaArgument.capture());
	}

	@Test
	public void shouldReturnUserBasedOnSpecifiedCriteria() throws Exception {
		// Given

		ArgumentCaptor<SimpleExpression> criteriaArgument = ArgumentCaptor
				.forClass(SimpleExpression.class);

		IdentityDao dao = new IdentityDao(sessionFactory);
		given(sessionFactory.getCurrentSession()).willReturn(session);
		given(session.createCriteria(Identity.class)).willReturn(criteria);
		given(criteria.add((Criterion) anyObject())).willReturn(criteria);
		given(criteria.uniqueResult()).willReturn(identity);

		// When
		try {
			dao.find("user", "password");
		} catch (InvalidCredentialsException exception) {
			Assert.fail("expected exception not thrown");

		}
		// Then
		verify(criteria, times(2)).add(criteriaArgument.capture());

		List<SimpleExpression> restrictions = criteriaArgument.getAllValues();
		SimpleExpression nameRestriction = Restrictions.eq("username", "user");
		SimpleExpression pwdRestriction = Restrictions.eq("password",
				"password");

		assertThat(restrictions.get(0), samePropertyValuesAs(nameRestriction));
		assertThat(restrictions.get(1), samePropertyValuesAs(pwdRestriction));

	}
}
