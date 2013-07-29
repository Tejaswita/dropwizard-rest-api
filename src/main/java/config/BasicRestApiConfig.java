package config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public class BasicRestApiConfig extends Configuration {

	@Valid
	@NotNull
	@JsonProperty
	private final DatabaseConfiguration database = new DatabaseConfiguration();

	public DatabaseConfiguration getDatabase() {
		return this.database;
	}

}
