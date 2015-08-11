package dao;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import model.User;
import org.junit.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;

public class UserDaoTest extends WithMongoServer {

    private Datastore datastore;
    private MongoClient mongoClient;

    @Before
    public void initialiseMongo() {
        mongoClient = new MongoClient("localhost", PORT);
        datastore = new Morphia().createDatastore(mongoClient, "test_db");
    }

    @After
    public void stopMongo(){
        mongoClient.close();
    }

    @Test
    public void itShouldAddNewUser(){
        String username = "username";
        String password = "password";
        User user = new User(username, password);
        UserDao userDao = new UserDao(datastore);

        userDao.create(user);

        List<User> actualUsers = datastore.createQuery(User.class).asList();
        assertThat(actualUsers.size(), is(1));
        assertThat(actualUsers.get(0).getUsername(), is(username));
        assertThat(actualUsers.get(0).getPassword(), is(password));
    }

    @Test
    public void itShouldRetrieveUserDetailsByUsername() {
        UserDao userDao = new UserDao(datastore);
        String username = "username";
        String password = "password";
        User expectedUser = new User(username, password);
        datastore.save(expectedUser);

        User anotherUser = new User("another", password);
        datastore.save(anotherUser);

        User actualUser = userDao.getUser(username);

        assertThat(actualUser, samePropertyValuesAs(expectedUser));
    }
}