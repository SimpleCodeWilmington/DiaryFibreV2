package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogPost;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class BlogPostRepositoryWithBagRelationshipsImpl implements BlogPostRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BlogPost> fetchBagRelationships(Optional<BlogPost> blogPost) {
        return blogPost.map(this::fetchTags);
    }

    @Override
    public Page<BlogPost> fetchBagRelationships(Page<BlogPost> blogPosts) {
        return new PageImpl<>(fetchBagRelationships(blogPosts.getContent()), blogPosts.getPageable(), blogPosts.getTotalElements());
    }

    @Override
    public List<BlogPost> fetchBagRelationships(List<BlogPost> blogPosts) {
        return Optional.of(blogPosts).map(this::fetchTags).orElse(Collections.emptyList());
    }

    BlogPost fetchTags(BlogPost result) {
        return entityManager
            .createQuery("select blogPost from BlogPost blogPost left join fetch blogPost.tags where blogPost is :blogPost", BlogPost.class)
            .setParameter("blogPost", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<BlogPost> fetchTags(List<BlogPost> blogPosts) {
        return entityManager
            .createQuery(
                "select distinct blogPost from BlogPost blogPost left join fetch blogPost.tags where blogPost in :blogPosts",
                BlogPost.class
            )
            .setParameter("blogPosts", blogPosts)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
