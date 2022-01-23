package com.example.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class TaskAppConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public TaskAppConfiguration() {

    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
        this.setDBConfig();
    }

    private void setDBConfig() {
        this.database.setUrl("jdbc:h2:mem:sample;ACCESS_MODE_DATA=rws");
        this.database.setDriverClass("org.h2.Driver");
        this.database.setUser("sa");
        this.database.setPassword("sa");
        this.database.getProperties().put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        this.database.getProperties().put("hibernate.hbm2ddl.auto", "create");
    }
}
