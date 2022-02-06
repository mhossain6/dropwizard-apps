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
        //this.setDBConfig();
        this.setOraDBConfig();
    }

    private void setDBConfig() {
        this.database.setUrl("jdbc:h2:/db/task;ACCESS_MODE_DATA=rws");
        this.database.setDriverClass("org.h2.Driver");
        this.database.setUser("sa");
        this.database.setPassword("sa");
        this.database.getProperties().put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        this.database.getProperties().put("hibernate.hbm2ddl.auto", "create");
    }

    private void setOraDBConfig() {

        this.database.setUrl("jdbc:oracle:thin:@tcps://<host>:<port>/<db>?wallet_location=<wallet_location>&ssl_server_cert_dn=<cert_dn>");
        this.database.setDriverClass("oracle.jdbc.driver.OracleDriver");
        this.database.setUser("hr");
        this.database.setPassword("<password>");
        this.database.getProperties().put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        this.database.setValidationQuery("select 1 from dual");

    }
}



