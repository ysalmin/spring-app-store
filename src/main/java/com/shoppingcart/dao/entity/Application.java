package com.shoppingcart.dao.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ysalmin on 22.07.2014.
 * DTO for Application.
 */
@NamedQueries({
        @NamedQuery(
                name = "getAppsByCategory",
                query = "select app from Application app join app.categories cat where cat.name = :catName"
        ),
        @NamedQuery(
                name = "getMostPopularApps",
                query = "select app from Application app order by app.downloads desc, app.timeUploaded desc"
        ),
        @NamedQuery(
                name = "getAppByName",
                query = "select app from Application app where app.name = :appName"
        )
 })
@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, max = 15)
    private String name;

    @Transient
    @Lob
    private MultipartFile file;

    @Size(min = 10)
    private String fileUrl;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeUploaded;

    @NotEmpty
    private String previewImageUrl;

    @NotEmpty
    private String detailedImageUrl;

    private int downloads;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "APPLICATION_CATEGORY",
                    joinColumns = {@JoinColumn(name = "APPLICATION_ID")},
                    inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    private Set<Category> categories = new HashSet<Category>();

    public Application() {
    }

    public Application(String name, String fileUrl, String previewImageUrl, String detailedImageUrl) {
        this.name = name;
        this.fileUrl = fileUrl;
        this.previewImageUrl = previewImageUrl;
        this.detailedImageUrl = detailedImageUrl;
    }

    public void addCategory(Category category, String categoryName) {
        if (category == null) {
            category = new Category(categoryName);
        }
        category.addApplication(this);
        categories.add(category);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public Date getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(Date timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getDetailedImageUrl() {
        return detailedImageUrl;
    }

    public void setDetailedImageUrl(String detailedImageUrl) {
        this.detailedImageUrl = detailedImageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
