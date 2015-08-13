package main;

import com.mongodb.MongoClient;
import core.MongoManaged;
import dao.ColorDao;
import dao.UserDao;
import healthcheck.MongoHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.java8.auth.basic.BasicAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.server.ServerProperties;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import resource.ColorCodeResource;
import service.UserAuthenticator;

import java.util.HashMap;
import java.util.Map;

public class RestApiMain extends Application<BasicRestApiConfig>{

    public static void main(String[] args) throws Exception {
        new RestApiMain().run(args);
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

        ColorCodeResource colorCodeResource = new ColorCodeResource(new ColorDao());
        environment.jersey().register(colorCodeResource);


        Map<String, Object> properties = new HashMap<>();
        properties.put(ServerProperties.WADL_FEATURE_DISABLE, false);
        environment.jersey().getResourceConfig().addProperties(properties);

    }

    @Override
    public void initialize(Bootstrap<BasicRestApiConfig> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new Java8Bundle());
    }
}
