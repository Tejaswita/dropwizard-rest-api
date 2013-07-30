package display;

import javax.ws.rs.core.MediaType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import resource.NameResource;

import com.sun.jersey.server.impl.template.ViewableMessageBodyWriter;
import com.yammer.dropwizard.testing.ResourceTest;

@RunWith(MockitoJUnitRunner.class)
public class DisplayNameAcceptanceTest extends ResourceTest {

	@Override
	protected void setUpResources() throws Exception {
		addResource(new NameResource());
		addProvider(ViewableMessageBodyWriter.class);
	}

	@Test
	public void shouldDisplayName() throws Exception {
		// Given
		String displayedName = client().resource("/display/testname")
				.type(MediaType.TEXT_HTML).get(String.class);
		// Then
		assertThat(displayedName, is("testname"));
	}
}
