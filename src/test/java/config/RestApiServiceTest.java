package config;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import resource.LoginResource;

import com.yammer.dropwizard.config.Environment;

@RunWith(MockitoJUnitRunner.class)
public class RestApiServiceTest {

	@Mock
	private Environment environment;
	private final BasicRestApiConfig configuration = new BasicRestApiConfig();

	@Before
	public void setup() {
	}

	@Ignore
	@Test
	public void shouldAddResources() {
		RestApiService service = new RestApiService();
		try {
			service.run(configuration, environment);
		} catch (Exception e) {
			Assert.fail("Exception not expected: " + e.getMessage());
		}

		verify(environment).addResource(any(LoginResource.class));
	}
}
