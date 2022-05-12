package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BlogImage;
import com.mycompany.myapp.repository.BlogImageRepository;
import com.mycompany.myapp.service.BlogImageService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BlogImage}.
 */
@RestController
@RequestMapping("/api")
public class BlogImageResource {

    private final Logger log = LoggerFactory.getLogger(BlogImageResource.class);

    private static final String ENTITY_NAME = "blogImage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogImageService blogImageService;

    private final BlogImageRepository blogImageRepository;

    public BlogImageResource(BlogImageService blogImageService, BlogImageRepository blogImageRepository) {
        this.blogImageService = blogImageService;
        this.blogImageRepository = blogImageRepository;
    }

    /**
     * {@code POST  /blog-images} : Create a new blogImage.
     *
     * @param blogImage the blogImage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogImage, or with status {@code 400 (Bad Request)} if the blogImage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-images")
    public ResponseEntity<BlogImage> createBlogImage(@Valid @RequestBody BlogImage blogImage) throws URISyntaxException {
        log.debug("REST request to save BlogImage : {}", blogImage);
        if (blogImage.getId() != null) {
            throw new BadRequestAlertException("A new blogImage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogImage result = blogImageService.save(blogImage);
        return ResponseEntity
            .created(new URI("/api/blog-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-images/:id} : Updates an existing blogImage.
     *
     * @param id the id of the blogImage to save.
     * @param blogImage the blogImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogImage,
     * or with status {@code 400 (Bad Request)} if the blogImage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-images/{id}")
    public ResponseEntity<BlogImage> updateBlogImage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlogImage blogImage
    ) throws URISyntaxException {
        log.debug("REST request to update BlogImage : {}, {}", id, blogImage);
        if (blogImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogImage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BlogImage result = blogImageService.update(blogImage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogImage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blog-images/:id} : Partial updates given fields of an existing blogImage, field will ignore if it is null
     *
     * @param id the id of the blogImage to save.
     * @param blogImage the blogImage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogImage,
     * or with status {@code 400 (Bad Request)} if the blogImage is not valid,
     * or with status {@code 404 (Not Found)} if the blogImage is not found,
     * or with status {@code 500 (Internal Server Error)} if the blogImage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blog-images/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlogImage> partialUpdateBlogImage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlogImage blogImage
    ) throws URISyntaxException {
        log.debug("REST request to partial update BlogImage partially : {}, {}", id, blogImage);
        if (blogImage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogImage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogImageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlogImage> result = blogImageService.partialUpdate(blogImage);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogImage.getId().toString())
        );
    }

    /**
     * {@code GET  /blog-images} : get all the blogImages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogImages in body.
     */
    @GetMapping("/blog-images")
    public List<BlogImage> getAllBlogImages() {
        log.debug("REST request to get all BlogImages");

        return blogImageService.findAll();
    }

    /**
     * {@code GET  /blog-images/:id} : get the "id" blogImage.
     *
     * @param id the id of the blogImage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogImage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-images/{id}")
    public ResponseEntity<BlogImage> getBlogImage(@PathVariable Long id) {
        log.debug("REST request to get BlogImage : {}", id);
        Optional<BlogImage> blogImage = blogImageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogImage);
    }

    /**
     * {@code DELETE  /blog-images/:id} : delete the "id" blogImage.
     *
     * @param id the id of the blogImage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-images/{id}")
    public ResponseEntity<Void> deleteBlogImage(@PathVariable Long id) {
        log.debug("REST request to delete BlogImage : {}", id);
        blogImageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
