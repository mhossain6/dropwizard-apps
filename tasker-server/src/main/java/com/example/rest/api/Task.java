package com.example.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "task")
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Version
    private int version;
    @Column(name = "checked", nullable = false)
    private String checked;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "date", nullable = false)
    private String date;

    public Task() {
        // Jackson deserialization
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

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
