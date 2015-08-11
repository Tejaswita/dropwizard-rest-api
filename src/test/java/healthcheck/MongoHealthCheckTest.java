package healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.same;

@RunWith(MockitoJUnitRunner.class)
public class MongoHealthCheckTest {

    @Mock
    private MongoClient mongoClient;

    @Test
    public void itShouldReturnHealthyStatusWhenMongoConnectionAvaialable() throws Exception {
        //Given

        String connectionPoint = "some connection point";
        given(mongoClient.getConnectPoint()).willReturn(connectionPoint);
        MongoHealthCheck healthCheck = new MongoHealthCheck(mongoClient);

        //Then
        HealthCheck.Result result = healthCheck.check();

        Assert.assertThat(result, is(HealthCheck.Result.healthy(connectionPoint)));
    }

}