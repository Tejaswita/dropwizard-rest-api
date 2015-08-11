package main;

import com.mongodb.MongoClient;
import core.MongoManaged;
import dao.UserDao;
import healthcheck.MongoHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.java8.auth.basic.BasicAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import model.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import service.UserAuthenticator;

public class MongodbExampleApplication extends Application<BasicRestApiConfig>{

    public static void main(String[] args) throws Exception {
        new MongodbExampleApplication().run(args);
    }

    @Override
    public void run(BasicRestApiConfig configuration, Environment environment) throws Exception {
        MongoClient mongoClient = new MongoClient(configuration.getHost(), configuration.getPort());
        Datastore datastore = new Morphia().createDatastore(mongoClient, configuration.getDbName());
        MongoHealthCheck mongoHealthCheck = new MongoHealthCheck(mongoClient);

        environment.lifecycle().manage(new MongoManaged(mongoClient));
        environment.healthChecks().register("mongo", mongoHealthCheck);

        final UserDao userDao = new UserDao(datastore);
        BasicAuthFactory<String> basicAuthFactory = new BasicAuthFactory<>(new UserAuthenticator(userDao), "Basic Auth", String.class);
        environment.jersey().register(basicAuthFactory);
        environment.jersey().register(userDao);
    }

    @Override
    public void initialize(Bootstrap<BasicRestApiConfig> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new Java8Bundle());
    }
}
