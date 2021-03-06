package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.AccessType;
import com.mycompany.myapp.domain.enumeration.Template;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Blog.
 */
@Entity
@Table(name = "blog")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "blog_name", nullable = false)
    private String blogName;

    @NotNull
    @Column(name = "blog_owner", nullable = false)
    private String blogOwner;

    @Enumerated(EnumType.STRING)
    @Column(name = "template")
    private Template template;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "access_status", nullable = false)
    private AccessType accessStatus;

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Blog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBlogName() {
        return this.blogName;
    }

    public Blog blogName(String blogName) {
        this.setBlogName(blogName);
        return this;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    public String getBlogOwner() {
        return this.blogOwner;
    }

    public Blog blogOwner(String blogOwner) {
        this.setBlogOwner(blogOwner);
        return this;
    }

    public void setBlogOwner(String blogOwner) {
        this.blogOwner = blogOwner;
    }

    public Template getTemplate() {
        return this.template;
    }

    public Blog template(Template template) {
        this.setTemplate(template);
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public AccessType getAccessStatus() {
        return this.accessStatus;
    }

    public Blog accessStatus(AccessType accessStatus) {
        this.setAccessStatus(accessStatus);
        return this;
    }

    public void setAccessStatus(AccessType accessStatus) {
        this.accessStatus = accessStatus;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Blog user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Blog)) {
            return false;
        }
        return id != null && id.equals(((Blog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Blog{" +
            "id=" + getId() +
            ", blogName='" + getBlogName() + "'" +
            ", blogOwner='" + getBlogOwner() + "'" +
            ", template='" + getTemplate() + "'" +
            ", accessStatus='" + getAccessStatus() + "'" +
            "}";
    }
}
