package com.TinyUrl.Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		"com.TinyUrl.Authentication",
		"com.TinyUrl.Controller",
		"com.TinyUrl.Services",
		"com.TinyUrl.Model",
		"com.TinyUrl.Services",
		"com.TinyUrl.Configurations"
		})
@EnableCassandraRepositories("com.TinyUrl.Repositories")
public class Bootstrap {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Bootstrap.class, args);
	}
}