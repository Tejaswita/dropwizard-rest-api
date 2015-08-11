package core;

import com.mongodb.MongoClient;
import io.dropwizard.lifecycle.Managed;
import org.eclipse.jetty.util.component.LifeCycle;

public class MongoManaged implements Managed {
    private MongoClient mongoClient;

    public MongoManaged(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        mongoClient.close();
    }
}
