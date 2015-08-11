package healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;

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
