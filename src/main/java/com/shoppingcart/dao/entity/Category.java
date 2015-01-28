package com.shoppingcart.dao.entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by ysalmin on 21.07.2014.
 * DTO for store categories.
 */
@NamedQueries({
        @NamedQuery(
                name = "getCategoryByName",
                query = "select cat from Category cat where cat.name = :catName"
        )
})
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min = 5, max = 30)
    String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Application> applications = new HashSet<Application>();

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public void addApplication(Application application) {
        applications.add(application);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!name.equals(category.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

}
