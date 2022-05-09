package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BlogText;
import com.mycompany.myapp.repository.BlogTextRepository;
import com.mycompany.myapp.service.BlogTextService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BlogText}.
 */
@RestController
@RequestMapping("/api")
public class BlogTextResource {

    private final Logger log = LoggerFactory.getLogger(BlogTextResource.class);

    private static final String ENTITY_NAME = "blogText";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogTextService blogTextService;

    private final BlogTextRepository blogTextRepository;

    public BlogTextResource(BlogTextService blogTextService, BlogTextRepository blogTextRepository) {
        this.blogTextService = blogTextService;
        this.blogTextRepository = blogTextRepository;
    }

    /**
     * {@code POST  /blog-texts} : Create a new blogText.
     *
     * @param blogText the blogText to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogText, or with status {@code 400 (Bad Request)} if the blogText has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-texts")
    public ResponseEntity<BlogText> createBlogText(@RequestBody BlogText blogText) throws URISyntaxException {
        log.debug("REST request to save BlogText : {}", blogText);
        if (blogText.getId() != null) {
            throw new BadRequestAlertException("A new blogText cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogText result = blogTextService.save(blogText);
        return ResponseEntity
            .created(new URI("/api/blog-texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-texts/:id} : Updates an existing blogText.
     *
     * @param id the id of the blogText to save.
     * @param blogText the blogText to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogText,
     * or with status {@code 400 (Bad Request)} if the blogText is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogText couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-texts/{id}")
    public ResponseEntity<BlogText> updateBlogText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlogText blogText
    ) throws URISyntaxException {
        log.debug("REST request to update BlogText : {}, {}", id, blogText);
        if (blogText.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogText.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BlogText result = blogTextService.update(blogText);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogText.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blog-texts/:id} : Partial updates given fields of an existing blogText, field will ignore if it is null
     *
     * @param id the id of the blogText to save.
     * @param blogText the blogText to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogText,
     * or with status {@code 400 (Bad Request)} if the blogText is not valid,
     * or with status {@code 404 (Not Found)} if the blogText is not found,
     * or with status {@code 500 (Internal Server Error)} if the blogText couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blog-texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlogText> partialUpdateBlogText(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BlogText blogText
    ) throws URISyntaxException {
        log.debug("REST request to partial update BlogText partially : {}, {}", id, blogText);
        if (blogText.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogText.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogTextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlogText> result = blogTextService.partialUpdate(blogText);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogText.getId().toString())
        );
    }

    /**
     * {@code GET  /blog-texts} : get all the blogTexts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogTexts in body.
     */
    @GetMapping("/blog-texts")
    public List<BlogText> getAllBlogTexts() {
        log.debug("REST request to get all BlogTexts");
        return blogTextService.findAll();
    }

    /**
     * {@code GET  /blog-texts/:id} : get the "id" blogText.
     *
     * @param id the id of the blogText to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogText, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-texts/{id}")
    public ResponseEntity<BlogText> getBlogText(@PathVariable Long id) {
        log.debug("REST request to get BlogText : {}", id);
        Optional<BlogText> blogText = blogTextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogText);
    }

    /**
     * {@code DELETE  /blog-texts/:id} : delete the "id" blogText.
     *
     * @param id the id of the blogText to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-texts/{id}")
    public ResponseEntity<Void> deleteBlogText(@PathVariable Long id) {
        log.debug("REST request to delete BlogText : {}", id);
        blogTextService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
