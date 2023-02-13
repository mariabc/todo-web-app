package ch.cern.todo.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TASK_CATEGORIES")
public class Category implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private long categoryId;

    @Column (name = "CATEGORY_NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Column (name = "CATEGORY_DESCRIPTION",  length = 500)
    private String description;

    protected Category() {
    }

    public Category(long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
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
}
