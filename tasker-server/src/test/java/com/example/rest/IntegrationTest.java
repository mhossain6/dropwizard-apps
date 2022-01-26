package com.example.rest;

import com.example.rest.api.Task;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

import static io.dropwizard.testing.ConfigOverride.config;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(DropwizardExtensionsSupport.class)
class IntegrationTest {
    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("test-example.yml");

    @TempDir
    static Path tempDir;
    static Supplier<String> CURRENT_LOG = () -> tempDir.resolve("application.log").toString();
    static Supplier<String> ARCHIVED_LOG = () -> tempDir.resolve("application-%d-%i.log.gz").toString();

    static final DropwizardAppExtension<TaskAppConfiguration> APP = new DropwizardAppExtension<>(
            TaskApplication.class, CONFIG_PATH,
            config("database.url", () -> "jdbc:h2:" + tempDir.resolve("database.h2")),
            config("logging.appenders[1].currentLogFilename", CURRENT_LOG),
            config("logging.appenders[1].archivedLogFilenamePattern", ARCHIVED_LOG)
    );


    @Test
    void testPostTask() throws Exception {
        final Task task = new Task("Simple Post Task", "2022-01-20");
        final Task newTask = postTask(task);
        assertThat(newTask.getDescription()).isEqualTo(task.getDescription());
        assertThat(newTask.getDate()).isEqualTo(task.getDate());
    }


    @Test
    void testQueryTask() throws Exception {
        final Task task = new Task("Test integration", "2022-01-20");
        final Task newTask = postTask(task);
        final String url = "http://localhost:" + APP.getLocalPort() + "/task/" + newTask.getId() + "/";
        Response response = APP.client().target(url).request().get();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
    }

    private Task postTask(Task task) {
        return APP.client().target("http://localhost:" + APP.getLocalPort() + "/task")
                .request()
                .post(Entity.entity(task, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Task.class);
    }

    @Test
    private void testLogFileWritten() throws IOException {

        final Path logFile = Paths.get(CURRENT_LOG.get());
        assertThat(logFile).exists();
        final String logFileContent = new String(Files.readAllBytes(logFile), UTF_8);
        assertThat(logFileContent)
                .contains("0.0.0.0:" + APP.getLocalPort(), "Starting hello-world", "Started application", "Started admin")
                .doesNotContain("Exception", "ERROR", "FATAL");
    }

    @Test
    void healthCheckShouldSucceed() {
        final Response healthCheckResponse =
                APP.client().target("http://localhost:" + APP.getLocalPort() + "/")
                        .request()
                        .get();

        assertThat(healthCheckResponse)
                .extracting(Response::getStatus)
                .isEqualTo(Response.Status.OK.getStatusCode());
    }
}
