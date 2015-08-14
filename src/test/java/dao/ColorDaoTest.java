package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.Color;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ColorDaoTest  extends WithMongoServer{

    private Datastore datastore;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {

        collection = client.getDatabase("testdb").getCollection("color");

        datastore = new Morphia().createDatastore(client, "test_db");
    }

    @Test
    public void itShouldSaveColor() {
        ColorDao dao = new ColorDao(datastore);
        Color redColor = new Color("red","#ff0000");

        dao.add(redColor);

        FindIterable<Color> colors = collection.find(Color.class);
        assertThat(colors.first().getColor(), is(redColor));

    }

    @Test
    public void itShouldReturnNoneIfNoCodeFound(){
        collection.insertOne(new Document("color","red").append("code","#ff0000"));

        ColorDao dao = new ColorDao(datastore);

        Color color = dao.getColor("red").get();

        assertThat(color.getColor(), is("red"));
        assertThat(color.getCode(), is("#ff0000"));

    }

}