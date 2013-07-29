package healthcheck;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.metrics.core.HealthCheck;

public class DBHealthCheck extends HealthCheck {

	private final DatabaseConfiguration database;

	public DBHealthCheck(DatabaseConfiguration database) {
		super("db healthcheck");
		this.database = database;
	}

	@Override
	protected Result check() throws Exception {

		return Result.healthy();

	}
}
