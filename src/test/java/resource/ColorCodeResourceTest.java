package resource;

import dao.ColorDao;
import model.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.swing.text.html.Option;
import javax.ws.rs.core.Response;

import java.util.Optional;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ColorCodeResourceTest {

    private ColorCodeResource colorCodeResource;
    @Mock
    private ColorDao colorDao;

    @Before
    public void setup() {
        colorCodeResource = new ColorCodeResource(colorDao);
    }

    @Test
    public void itShouldReturnRgbCodeForColor(){
        Color white = new Color("white", "#ffffff");
        given(colorDao.getColor("white")).willReturn(Optional.of(white));

        Response rgbCode = colorCodeResource.get("white");


        assertThat((String)rgbCode.getEntity(), is("#ffffff"));
        assertThat(rgbCode.getStatus(), is(OK.getStatusCode()));
        verify(colorDao).getColor("white");
    }

    @Test
    public void itShouldReturnNotFoundResponseIfNoMatchingCode() {
        Color white = new Color("white", "#ffffff");

        given(colorDao.getColor("white")).willReturn(Optional.of(white));
        Response rgbCode = colorCodeResource.get("white");

        assertThat((String)rgbCode.getEntity(), is("#ffffff"));
        assertThat(rgbCode.getStatus(), is(OK.getStatusCode()));
    }

    @Test
    public void itShouldAddColorCodes(){
        Color color = new Color("white","#ffffff");
        given(colorDao.add(color)).willReturn("white");

        Response response = colorCodeResource.add(color);

        assertThat(response.getStatus(), is(CREATED.getStatusCode()));
        verify(colorDao).add(color);
    }


}
