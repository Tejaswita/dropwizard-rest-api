package main;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class BasicRestApiConfig extends Configuration {

	@NotNull
	@Valid
	@JsonProperty
	private String host;

	@NotNull
	@Valid
	@JsonProperty
	private int port;

	@NotNull
	@Valid
	@JsonProperty
	private String dbName;

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getDbName() {
		return dbName;
	}
}
