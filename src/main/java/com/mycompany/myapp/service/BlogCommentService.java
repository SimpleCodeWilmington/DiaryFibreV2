package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BlogComment;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BlogComment}.
 */
public interface BlogCommentService {
    /**
     * Save a blogComment.
     *
     * @param blogComment the entity to save.
     * @return the persisted entity.
     */
    BlogComment save(BlogComment blogComment);

    /**
     * Updates a blogComment.
     *
     * @param blogComment the entity to update.
     * @return the persisted entity.
     */
    BlogComment update(BlogComment blogComment);

    /**
     * Partially updates a blogComment.
     *
     * @param blogComment the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlogComment> partialUpdate(BlogComment blogComment);

    /**
     * Get all the blogComments.
     *
     * @return the list of entities.
     */
    List<BlogComment> findAll();

    /**
     * Get the "id" blogComment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogComment> findOne(Long id);

    /**
     * Delete the "id" blogComment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
