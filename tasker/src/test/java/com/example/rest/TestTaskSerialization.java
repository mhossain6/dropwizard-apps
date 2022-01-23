package com.example.rest;


import com.example.rest.api.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.jupiter.api.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class TestTaskSerialization {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @Test
    public void serializesToJSON() throws Exception {
        final Task person = new Task(1, "Do serial test", "2022-01-20");

        final String expected = MAPPER.writeValueAsString(
                MAPPER.readValue(fixture("fixtures/task.json"), Task.class));

        assertThat(MAPPER.writeValueAsString(person)).isEqualTo(expected);
    }
}