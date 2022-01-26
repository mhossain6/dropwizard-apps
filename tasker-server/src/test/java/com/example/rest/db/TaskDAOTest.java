package com.example.rest.db;

import com.example.rest.api.Task;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(DropwizardExtensionsSupport.class)
class TaskDAOTest {

    public DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
            .addEntityClass(Task.class)
            .build();

    private TaskDAO taskDAO;

    @BeforeEach
    void setUp() throws Exception {
        taskDAO = new TaskDAO(daoTestRule.getSessionFactory());
    }

    @Test
    void createTask() {
        final Task jeff = daoTestRule.inTransaction(() -> taskDAO.createTask(new Task("Simple Task To Do", "2022-01-20")));
        assertThat(jeff.getId()).isPositive();
        assertThat(jeff.getDescription()).isEqualTo("Simple Task To Do");
        assertThat(jeff.getDate()).isEqualTo("2022-01-20");
        assertThat(taskDAO.findById(jeff.getId())).isEqualTo(Optional.of(jeff));
    }

    @Test
    void findAll() {
        daoTestRule.inTransaction(() -> {
            taskDAO.createTask(new Task("Simple Task To Do", "2022-01-20"));
            taskDAO.createTask(new Task("Complex Task To Do", "2022-01-28"));
            taskDAO.createTask(new Task("More Complex Task To Do", "2022-01-30"));
        });

        final List<Task> tasks = taskDAO.findAll();
        assertThat(tasks).extracting("description").containsOnly("Simple Task To Do", "Complex Task To Do", "More Complex Task To Do");
        assertThat(tasks).extracting("date").containsOnly("2022-01-20", "2022-01-28", "2022-01-30");
    }

    @Test
    void handlesNullDescription() throws Exception {
        assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                daoTestRule.inTransaction(() -> taskDAO.createTask(new Task(null, null))));
    }
}
