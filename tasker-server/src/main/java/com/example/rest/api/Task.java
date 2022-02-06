package com.example.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "HR.TASK")
@NamedQueries(
        {
                @NamedQuery(
                        name = "findAllTask",
                        query = "SELECT p FROM Task p"
                ),
                @NamedQuery(
                        name = "findTaskById",
                        query = "SELECT p FROM Task p where p.id = :id"
                ),
                @NamedQuery(
                        name = "deleteTaskById",
                        query = "DELETE FROM Task p where p.id = :id"
                )
        })

public class Task {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_generator")
    @SequenceGenerator(name = "c_generator", sequenceName = "HR.TASK_SEQ", allocationSize = 1)
    private long id;
    @Version
    @Column(name = "VERSION", nullable = false)
    private int version;
    @Column(name = "CHECKED", nullable = true)
    private String checked;
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
    @Column(name = "TARGET_DATE", nullable = true)
    private String date;

    public Task() {
        // Jackson deserialization
        version = 0;
    }

    public Task(long id, String description, String date) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.checked = "N";
    }

    public Task(String description, String date) {
        this.description = description;
        this.date = date;
        this.checked = "N";
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonProperty("checked")
    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Task)) {
            return false;
        }

        Task task = (Task) o;

        return hashCode() == task.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, date);
    }
}
