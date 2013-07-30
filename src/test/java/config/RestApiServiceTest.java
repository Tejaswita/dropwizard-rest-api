package config;

import healthcheck.LoginHealthCheck;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.isA;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import resource.LoginResource;
import resource.NameResource;

import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.hibernate.HibernateBundle;

@RunWith(MockitoJUnitRunner.class)
public class RestApiServiceTest {

	@Mock
	private Environment environment;
	private final BasicRestApiConfig configuration = new BasicRestApiConfig();
	@Mock
	private HibernateBundle<BasicRestApiConfig> hibernate;
	@Mock
	private SessionFactory sessionFactory;
	private RestApiService service;

	@Before
	public void setup() {
		service = new RestApiService();

		given(hibernate.getSessionFactory()).willReturn(sessionFactory);
		Whitebox.setInternalState(service, "hibernate", hibernate);
	}

	@Test
	public void shouldAddResources() {

		try {
			service.run(configuration, environment);
		} catch (Exception e) {
			Assert.fail("Exception not expected: " + e.getMessage());
		}

		verify(environment, times(1)).addResource(isA(LoginResource.class));
		verify(environment, times(1)).addResource(isA(NameResource.class));
	}

	@Test
	public void shouldAddHealthCheckForLoginResource() {

		try {
			service.run(configuration, environment);
		} catch (Exception e) {
			Assert.fail("Exception not expected: " + e.getMessage());
		}

		verify(environment).addHealthCheck(isA(LoginHealthCheck.class));
	}
}
