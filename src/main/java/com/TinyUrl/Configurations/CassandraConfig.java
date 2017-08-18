package com.TinyUrl.Configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.DropKeyspaceSpecification;
import org.springframework.cassandra.core.keyspace.KeyspaceOption;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraTemplate;

@Configuration
@EnableAutoConfiguration
@PropertySource({"classpath:/configurations/cassandra.properties"})
public class CassandraConfig extends AbstractCassandraConfiguration{
	
	@Autowired
	private Environment env;

	@Override
	protected String getKeyspaceName() {	
		return env.getProperty("spring.data.cassandra.keyspace-name");
	}

	@Override
	public String[] getEntityBasePackages() {
		return new String[] { "com.TinyURL"};
	}

	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}
	
	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations(){
		if(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).getIfNotExists())
		return Arrays.asList(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).with(KeyspaceOption.DURABLE_WRITES, true));
		else
			return Arrays.asList(CreateKeyspaceSpecification.createKeyspace(getKeyspaceName()).ifNotExists());
	}
	
	/*@Override
	protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
		return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(getKeyspaceName()));
	}*/
}
