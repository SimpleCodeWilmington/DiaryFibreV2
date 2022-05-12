package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlogComment.
 */
@Entity
@Table(name = "blog_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "blogImages", "blogComments", "blog", "tags" }, allowSetters = true)
    private BlogPost blogPost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlogComment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return this.comment;
    }

    public BlogComment comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public BlogComment dateTime(ZonedDateTime dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogComment user(User user) {
        this.setUser(user);
        return this;
    }

    public BlogPost getBlogPost() {
        return this.blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public BlogComment blogPost(BlogPost blogPost) {
        this.setBlogPost(blogPost);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogComment)) {
            return false;
        }
        return id != null && id.equals(((BlogComment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogComment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            "}";
    }
}
