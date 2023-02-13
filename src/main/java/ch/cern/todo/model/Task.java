package ch.cern.todo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TASKS")
public class Task implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "TASK_ID")
    private long taskId;
    @Column(name = "TASK_NAME", nullable = false, length = 100)
    private String name;
    @Column(name = "TASK_DESCRIPTION", length = 500)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "DEADLINE", nullable = false)
    private Date deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    protected Task() {
    }

    public Task(long taskId, String name, String description, Date deadline, Category category) {
        this.taskId = taskId;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.category = category;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}