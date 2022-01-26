package com.example.rest.resource;


import com.example.rest.api.Task;
import com.example.rest.db.TaskDAO;
import com.example.rest.resources.TaskResource;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.core.Response;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link TaskResource}.
 */
@ExtendWith(DropwizardExtensionsSupport.class)
class TaskResourceTest {
    private static final TaskDAO DAO = mock(TaskDAO.class);

    public static final ResourceExtension RULE = ResourceExtension.builder()
            .addResource(new TaskResource(DAO))
            .build();
    private Task task;

    @BeforeEach
    void setup() {
        task = new Task();
        task.setId(1L);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getTaskSuccess() {
        when(DAO.findById(1L)).thenReturn(Optional.of(task));

        Task found = RULE.target("/task/1").request().get(Task.class);

        assertThat(found.getId()).isEqualTo(task.getId());
        verify(DAO).findById(1L);
    }

    @Test
    void getTaskNotFound() throws Exception {
        when(DAO.findById(2L)).thenReturn(Optional.empty());
        final Response response = RULE.target("/task/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2L);
    }
}
