package dao;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import org.junit.After;
import org.junit.Before;

import java.net.InetSocketAddress;

/**
 * Created by Tejaswita on 11-08-2015.
 */
public class WithMongoServer {
    protected MongoServer server;
    protected MongoClient client;

    @Before
    public void setUp() {
        server = new MongoServer(new MemoryBackend());

        // bind on a random local port
        InetSocketAddress serverAddress = server.bind();

        client = new MongoClient(new ServerAddress(serverAddress));
    }

    @After
    public void tearDown() {
        client.close();
        server.shutdownNow();
    }

}
