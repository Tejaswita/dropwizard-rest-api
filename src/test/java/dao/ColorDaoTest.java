package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import model.Color;
import org.bson.BSON;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import javax.xml.parsers.DocumentBuilder;
import java.util.Optional;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ColorDaoTest  extends WithMongoServer{

    private Datastore datastore;
    private MongoCollection<Document> collection;

    @Before
    public void setup() {
        collection = client.getDatabase("test_db").getCollection("color");

        datastore = new Morphia().createDatastore(client, "test_db");
    }

    @Test
    public void itShouldSaveColor() {
        ColorDao dao = new ColorDao(datastore);
        Color redColor = new Color("red","#ff0000");

        dao.add(redColor);

        Bson filter = BsonDocument.parse("{'color':'red'}");
        Document color = collection.find(filter).first();
        assertThat(color, hasEntry("color","red"));
        assertThat(color, hasEntry("code","#ff0000"));

    }

    @Test
    public void itShouldReturnCode(){
        collection.insertOne(new Document("color","red").append("code","#ff0000"));
        collection.insertOne(new Document("color","white").append("code","#ffffff"));
        ColorDao dao = new ColorDao(datastore);

        Optional<Color> color = dao.getColor("white");
        assertThat(color.get().getCode(), is("#ffffff"));

        Optional<Color> red = dao.getColor("red");
        assertThat(red.get().getCode(), is("#ff0000"));
    }

    @Test
    public void itShouldReturnNoneIfNoCodeFound(){
        collection.insertOne(new Document("color","red").append("code","#ff0000"));

        ColorDao dao = new ColorDao(datastore);

        Optional<Color> color = dao.getColor("white");

        assertThat(color, is(Optional.empty()));
    }

}