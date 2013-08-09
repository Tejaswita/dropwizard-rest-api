package config;

import model.Identity;
import healthcheck.LoginHealthCheck;
import resource.LoginResource;
import resource.NameResource;
import service.AuthenticationService;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;

import dao.IdentityDao;

public class RestApiService extends Service<BasicRestApiConfig> {

	public static void main(String[] args) throws Exception {
		new RestApiService().run(args);
	}

	private final HibernateBundle<BasicRestApiConfig> hibernate = new HibernateBundle<BasicRestApiConfig>(
			Identity.class) {
		@Override
		public DatabaseConfiguration getDatabaseConfiguration(
				BasicRestApiConfig configuration) {
			return configuration.getDatabase();
		}

	};

	@Override
	public void initialize(Bootstrap<BasicRestApiConfig> bootstrap) {
		bootstrap.setName("dropwizard-rest-api");
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(BasicRestApiConfig configuration, Environment environment)
			throws Exception {
		final IdentityDao identityDao = new IdentityDao(
				hibernate.getSessionFactory());
		AuthenticationService authService = new AuthenticationService(
				identityDao);
		LoginResource loginResource = new LoginResource(authService);
		environment.addResource(loginResource);
		environment.addResource(new NameResource());
		environment.addHealthCheck(new LoginHealthCheck(loginResource));
	}

}
