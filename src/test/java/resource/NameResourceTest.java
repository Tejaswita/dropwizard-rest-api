package resource;

import javax.ws.rs.WebApplicationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

public class NameResourceTest {

	private NameResource resource;

	@Before
	public void setup() {
		resource = new NameResource();
	}

	@Test
	public void shouldDisplayNameFromPath() {
		String name = "name";

		String displayedName = resource.display(name);

		assertThat(displayedName, is(name));
	}

	@Test(expected = WebApplicationException.class)
	public void shouldThrowWebAppExceptionForEmptyName() {
		String name = "";

		resource.display(name);
	}
}
