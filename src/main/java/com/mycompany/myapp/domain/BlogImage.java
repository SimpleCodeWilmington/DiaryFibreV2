package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlogImage.
 */
@Entity
@Table(name = "blog_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlogImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "blog_image", nullable = false)
    private byte[] blogImage;

    @NotNull
    @Column(name = "blog_image_content_type", nullable = false)
    private String blogImageContentType;

    @NotNull
    @Column(name = "image_number", nullable = false)
    private Integer imageNumber;

    @ManyToOne
    @JsonIgnoreProperties(value = { "blogImages", "blog", "tags" }, allowSetters = true)
    private BlogPost blogpost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlogImage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBlogImage() {
        return this.blogImage;
    }

    public BlogImage blogImage(byte[] blogImage) {
        this.setBlogImage(blogImage);
        return this;
    }

    public void setBlogImage(byte[] blogImage) {
        this.blogImage = blogImage;
    }

    public String getBlogImageContentType() {
        return this.blogImageContentType;
    }

    public BlogImage blogImageContentType(String blogImageContentType) {
        this.blogImageContentType = blogImageContentType;
        return this;
    }

    public void setBlogImageContentType(String blogImageContentType) {
        this.blogImageContentType = blogImageContentType;
    }

    public Integer getImageNumber() {
        return this.imageNumber;
    }

    public BlogImage imageNumber(Integer imageNumber) {
        this.setImageNumber(imageNumber);
        return this;
    }

    public void setImageNumber(Integer imageNumber) {
        this.imageNumber = imageNumber;
    }

    public BlogPost getBlogpost() {
        return this.blogpost;
    }

    public void setBlogpost(BlogPost blogPost) {
        this.blogpost = blogPost;
    }

    public BlogImage blogpost(BlogPost blogPost) {
        this.setBlogpost(blogPost);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogImage)) {
            return false;
        }
        return id != null && id.equals(((BlogImage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogImage{" +
            "id=" + getId() +
            ", blogImage='" + getBlogImage() + "'" +
            ", blogImageContentType='" + getBlogImageContentType() + "'" +
            ", imageNumber=" + getImageNumber() +
            "}";
    }
}
