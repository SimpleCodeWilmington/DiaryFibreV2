package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BlogImage;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BlogImage}.
 */
public interface BlogImageService {
    /**
     * Save a blogImage.
     *
     * @param blogImage the entity to save.
     * @return the persisted entity.
     */
    BlogImage save(BlogImage blogImage);

    /**
     * Updates a blogImage.
     *
     * @param blogImage the entity to update.
     * @return the persisted entity.
     */
    BlogImage update(BlogImage blogImage);

    /**
     * Partially updates a blogImage.
     *
     * @param blogImage the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BlogImage> partialUpdate(BlogImage blogImage);

    /**
     * Get all the blogImages.
     *
     * @return the list of entities.
     */
    List<BlogImage> findAll();

    /**
     * Get the "id" blogImage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BlogImage> findOne(Long id);

    /**
     * Delete the "id" blogImage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
