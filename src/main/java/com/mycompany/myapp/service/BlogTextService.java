package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BlogText;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BlogText}.
 */
public interface BlogTextService {
    /**
     * Save a blogText.
     *
     * @param blogText the entity to save.
     * @return the persisted entity.
     */
    BlogText save(BlogText blogText);

    /**
     * Updates a blogText.
     *
     * @param blogText the entity to update.
     * @return the persisted entity.
     */
    BlogText update(BlogText blogText);

    /**
     * Partially updates a blogText.
     *
     * @param blogText the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlogText> partialUpdate(BlogText blogText);

    /**
     * Get all the blogTexts.
     *
     * @return the list of entities.
     */
    List<BlogText> findAll();
    /**
     * Get all the BlogText where Blogpost is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<BlogText> findAllWhereBlogpostIsNull();

    /**
     * Get the "id" blogText.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogText> findOne(Long id);

    /**
     * Delete the "id" blogText.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
