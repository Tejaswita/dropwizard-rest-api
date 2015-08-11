package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Tejaswita on 11-08-2015.
 */
public class ColorDaoTest extends WithMongoServer {

    @Test
    public void itShouldSaveColorAndCode() {
        MongoClient client = new MongoClient("localhost", PORT);
        MongoDatabase testDb = client.getDatabase("test_db");
    }


}