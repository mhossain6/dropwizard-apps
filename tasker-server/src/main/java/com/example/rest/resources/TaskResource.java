package com.example.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.example.rest.api.Task;
import com.example.rest.db.TaskDAO;
import com.example.rest.provider.Dropwizard404Exception;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private static final Logger logger = LoggerFactory.getLogger(TaskResource.class);

    private final TaskDAO taskDAO;
    private final AtomicLong counter;

    public TaskResource(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    @UnitOfWork
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getTask() {
        return taskDAO.findAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTask(@PathParam("id") Long id) throws Dropwizard404Exception {
        Optional<Task> response = taskDAO.findById(id);
        if (response.isPresent())
            return Response.ok(response.get()).build();
        else
            throw new Dropwizard404Exception(404, "No Object Found with the ID");
    }

    @POST
    @Timed
    @UnitOfWork
    @Transactional
    public Task createOrReplaceTask(@Valid final Task task) {
        return taskDAO.createOrReplace(task);
    }

    @PUT
    @Timed
    @UnitOfWork
    @Transactional
    public Task createTask(@Valid final Task task) {
        logger.info("Received a task: {}", task);
        return taskDAO.createTask(task);
    }

    @DELETE
    @Path("/{id}")
    @Timed
    @UnitOfWork
    @Transactional
    public Response deleteTask(@PathParam("id") Long id) throws Dropwizard404Exception {
        logger.info("Received for deletion with id {}", id);
        Integer rowCount = taskDAO.deleteTask(id);
        if (rowCount > 0)
            return Response.ok(rowCount).build();
        else
            throw new Dropwizard404Exception(404, "No Object Found with the ID");
    }
}