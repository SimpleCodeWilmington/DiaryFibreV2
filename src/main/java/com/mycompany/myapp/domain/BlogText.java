package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BlogText.
 */
@Entity
@Table(name = "blog_text")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BlogText implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "blog_text")
    private byte[] blogText;

    @Column(name = "blog_text_content_type")
    private String blogTextContentType;

    @JsonIgnoreProperties(value = { "blogtext", "blogImages", "blog", "tags" }, allowSetters = true)
    @OneToOne(mappedBy = "blogtext")
    private BlogPost blogpost;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BlogText id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBlogText() {
        return this.blogText;
    }

    public BlogText blogText(byte[] blogText) {
        this.setBlogText(blogText);
        return this;
    }

    public void setBlogText(byte[] blogText) {
        this.blogText = blogText;
    }

    public String getBlogTextContentType() {
        return this.blogTextContentType;
    }

    public BlogText blogTextContentType(String blogTextContentType) {
        this.blogTextContentType = blogTextContentType;
        return this;
    }

    public void setBlogTextContentType(String blogTextContentType) {
        this.blogTextContentType = blogTextContentType;
    }

    public BlogPost getBlogpost() {
        return this.blogpost;
    }

    public void setBlogpost(BlogPost blogPost) {
        if (this.blogpost != null) {
            this.blogpost.setBlogtext(null);
        }
        if (blogPost != null) {
            blogPost.setBlogtext(this);
        }
        this.blogpost = blogPost;
    }

    public BlogText blogpost(BlogPost blogPost) {
        this.setBlogpost(blogPost);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlogText)) {
            return false;
        }
        return id != null && id.equals(((BlogText) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BlogText{" +
            "id=" + getId() +
            ", blogText='" + getBlogText() + "'" +
            ", blogTextContentType='" + getBlogTextContentType() + "'" +
            "}";
    }
}
