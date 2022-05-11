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

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "template")
    private Template template;

    @OneToMany(mappedBy = "blogpost")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "blogpost" }, allowSetters = true)
    private Set<BlogImage> blogImages = new HashSet<>();

    @OneToMany(mappedBy = "blogPost")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "blogPost" }, allowSetters = true)
    private Set<BlogComment> blogComments = new HashSet<>();

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

    public String getTitle() {
        return this.title;
    }

    public BlogPost title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public BlogPost text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
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

    public Set<BlogComment> getBlogComments() {
        return this.blogComments;
    }

    public void setBlogComments(Set<BlogComment> blogComments) {
        if (this.blogComments != null) {
            this.blogComments.forEach(i -> i.setBlogPost(null));
        }
        if (blogComments != null) {
            blogComments.forEach(i -> i.setBlogPost(this));
        }
        this.blogComments = blogComments;
    }

    public BlogPost blogComments(Set<BlogComment> blogComments) {
        this.setBlogComments(blogComments);
        return this;
    }

    public BlogPost addBlogComment(BlogComment blogComment) {
        this.blogComments.add(blogComment);
        blogComment.setBlogPost(this);
        return this;
    }

    public BlogPost removeBlogComment(BlogComment blogComment) {
        this.blogComments.remove(blogComment);
        blogComment.setBlogPost(null);
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
            ", title='" + getTitle() + "'" +
            ", text='" + getText() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
