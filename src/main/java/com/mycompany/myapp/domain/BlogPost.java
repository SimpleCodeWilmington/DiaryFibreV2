package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Template;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "template", nullable = false)
    private Template template;

    @JsonIgnoreProperties(value = { "blogpost" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private BlogText blogtext;

    @OneToMany(mappedBy = "blogpost")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "blogpost" }, allowSetters = true)
    private Set<BlogImage> blogImages = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Blog blog;

    @ManyToMany
    @JoinTable(
        name = "rel_blog_post__tag",
        joinColumns = @JoinColumn(name = "blog_post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "blogposts" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

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

    public BlogText getBlogtext() {
        return this.blogtext;
    }

    public void setBlogtext(BlogText blogText) {
        this.blogtext = blogText;
    }

    public BlogPost blogtext(BlogText blogText) {
        this.setBlogtext(blogText);
        return this;
    }

    public Set<BlogImage> getBlogImages() {
        return this.blogImages;
    }

    public void setBlogImages(Set<BlogImage> blogImages) {
        if (this.blogImages != null) {
            this.blogImages.forEach(i -> i.setBlogpost(null));
        }
        if (blogImages != null) {
            blogImages.forEach(i -> i.setBlogpost(this));
        }
        this.blogImages = blogImages;
    }

    public BlogPost blogImages(Set<BlogImage> blogImages) {
        this.setBlogImages(blogImages);
        return this;
    }

    public BlogPost addBlogImage(BlogImage blogImage) {
        this.blogImages.add(blogImage);
        blogImage.setBlogpost(this);
        return this;
    }

    public BlogPost removeBlogImage(BlogImage blogImage) {
        this.blogImages.remove(blogImage);
        blogImage.setBlogpost(null);
        return this;
    }

    public Blog getBlog() {
        return this.blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public BlogPost blog(Blog blog) {
        this.setBlog(blog);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public BlogPost tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public BlogPost addTag(Tag tag) {
        this.tags.add(tag);
        tag.getBlogposts().add(this);
        return this;
    }

    public BlogPost removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getBlogposts().remove(this);
        return this;
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
            ", dateTime='" + getDateTime() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}