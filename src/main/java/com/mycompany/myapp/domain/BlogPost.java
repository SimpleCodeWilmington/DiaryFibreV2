package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.enumeration.Template;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlogPost.
 */
@Entity
@Table(name = "blog_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlogPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "blog_id", nullable = false)
    private Long blogID;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "template", nullable = false)
    private Template template;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlogPost id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBlogID() {
        return this.blogID;
    }

    public BlogPost blogID(Long blogID) {
        this.setBlogID(blogID);
        return this;
    }

    public void setBlogID(Long blogID) {
        this.blogID = blogID;
    }

    public ZonedDateTime getDateTime() {
        return this.dateTime;
    }

    public BlogPost dateTime(ZonedDateTime dateTime) {
        this.setDateTime(dateTime);
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Template getTemplate() {
        return this.template;
    }

    public BlogPost template(Template template) {
        this.setTemplate(template);
        return this;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogPost)) {
            return false;
        }
        return id != null && id.equals(((BlogPost) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogPost{" +
            "id=" + getId() +
            ", blogID=" + getBlogID() +
            ", dateTime='" + getDateTime() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
