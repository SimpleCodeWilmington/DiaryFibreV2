package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BlogPost;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link BlogPost}.
 */
public interface BlogPostService {
    /**
     * Save a blogPost.
     *
     * @param blogPost the entity to save.
     * @return the persisted entity.
     */
    BlogPost save(BlogPost blogPost);

    /**
     * Updates a blogPost.
     *
     * @param blogPost the entity to update.
     * @return the persisted entity.
     */
    BlogPost update(BlogPost blogPost);

    /**
     * Partially updates a blogPost.
     *
     * @param blogPost the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlogPost> partialUpdate(BlogPost blogPost);

    /**
     * Get all the blogPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BlogPost> findAll(Pageable pageable);

    /**
     * Get the "id" blogPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogPost> findOne(Long id);

    /**
     * Delete the "id" blogPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
