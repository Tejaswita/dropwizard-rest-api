package healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import com.mongodb.util.JSON;
import org.apache.commons.lang3.StringUtils;

public class MongoHealthCheck extends HealthCheck {

    private MongoClient mongoClient;

    public MongoHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        String connectPoint = mongoClient.getConnectPoint();
        return Result.healthy(connectPoint);
    }
}
