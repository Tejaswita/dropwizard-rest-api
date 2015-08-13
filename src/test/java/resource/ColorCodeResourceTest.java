package resource;

import dao.ColorDao;
import model.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        String color = "white";
        String colorCode = "#ffffff";
        given(colorDao.getCode(color)).willReturn(colorCode);

        Response rgbCode = colorCodeResource.get(color);


        assertThat((String)rgbCode.getEntity(), is(colorCode));
        assertThat(rgbCode.getStatus(), is(OK.getStatusCode()));
        verify(colorDao).getCode(color);
    }

    @Test
    public void itShouldReturnNotFoundResponseIfNoMatchingCode() {
        String color = "red";
        String colorCode = "#ffffff";

        given(colorDao.getCode(color)).willReturn(colorCode);
        Response rgbCode = colorCodeResource.get(color);

        Entity code = Entity.entity(colorCode, MediaType.APPLICATION_JSON_TYPE);
        assertThat((String)rgbCode.getEntity(), is(code.getEntity()));
        assertThat(rgbCode.getStatus(), is(OK.getStatusCode()));
    }

    @Test
    public void itShouldAddColorCodes(){
        Color color = new Color("white","#ffffff");
        Response response = colorCodeResource.add(color);

        assertThat(response.getStatus(), is(CREATED.getStatusCode()));
        verify(colorDao).add(color);
    }


}
