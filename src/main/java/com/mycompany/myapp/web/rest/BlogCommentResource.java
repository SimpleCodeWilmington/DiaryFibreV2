package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.BlogComment;
import com.mycompany.myapp.repository.BlogCommentRepository;
import com.mycompany.myapp.service.BlogCommentService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.BlogComment}.
 */
@RestController
@RequestMapping("/api")
public class BlogCommentResource {

    private final Logger log = LoggerFactory.getLogger(BlogCommentResource.class);

    private static final String ENTITY_NAME = "blogComment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogCommentService blogCommentService;

    private final BlogCommentRepository blogCommentRepository;

    public BlogCommentResource(BlogCommentService blogCommentService, BlogCommentRepository blogCommentRepository) {
        this.blogCommentService = blogCommentService;
        this.blogCommentRepository = blogCommentRepository;
    }

    /**
     * {@code POST  /blog-comments} : Create a new blogComment.
     *
     * @param blogComment the blogComment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogComment, or with status {@code 400 (Bad Request)} if the blogComment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-comments")
    public ResponseEntity<BlogComment> createBlogComment(@Valid @RequestBody BlogComment blogComment) throws URISyntaxException {
        log.debug("REST request to save BlogComment : {}", blogComment);
        if (blogComment.getId() != null) {
            throw new BadRequestAlertException("A new blogComment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogComment result = blogCommentService.save(blogComment);
        return ResponseEntity
            .created(new URI("/api/blog-comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-comments/:id} : Updates an existing blogComment.
     *
     * @param id the id of the blogComment to save.
     * @param blogComment the blogComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogComment,
     * or with status {@code 400 (Bad Request)} if the blogComment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-comments/{id}")
    public ResponseEntity<BlogComment> updateBlogComment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BlogComment blogComment
    ) throws URISyntaxException {
        log.debug("REST request to update BlogComment : {}, {}", id, blogComment);
        if (blogComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogComment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BlogComment result = blogCommentService.update(blogComment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogComment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /blog-comments/:id} : Partial updates given fields of an existing blogComment, field will ignore if it is null
     *
     * @param id the id of the blogComment to save.
     * @param blogComment the blogComment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogComment,
     * or with status {@code 400 (Bad Request)} if the blogComment is not valid,
     * or with status {@code 404 (Not Found)} if the blogComment is not found,
     * or with status {@code 500 (Internal Server Error)} if the blogComment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/blog-comments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BlogComment> partialUpdateBlogComment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BlogComment blogComment
    ) throws URISyntaxException {
        log.debug("REST request to partial update BlogComment partially : {}, {}", id, blogComment);
        if (blogComment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, blogComment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!blogCommentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BlogComment> result = blogCommentService.partialUpdate(blogComment);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogComment.getId().toString())
        );
    }

    /**
     * {@code GET  /blog-comments} : get all the blogComments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogComments in body.
     */
    @GetMapping("/blog-comments")
    public List<BlogComment> getAllBlogComments() {
        log.debug("REST request to get all BlogComments");
        return blogCommentService.findAll();
    }

    /**
     * {@code GET  /blog-comments/:id} : get the "id" blogComment.
     *
     * @param id the id of the blogComment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogComment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-comments/{id}")
    public ResponseEntity<BlogComment> getBlogComment(@PathVariable Long id) {
        log.debug("REST request to get BlogComment : {}", id);
        Optional<BlogComment> blogComment = blogCommentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(blogComment);
    }

    /**
     * {@code DELETE  /blog-comments/:id} : delete the "id" blogComment.
     *
     * @param id the id of the blogComment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-comments/{id}")
    public ResponseEntity<Void> deleteBlogComment(@PathVariable Long id) {
        log.debug("REST request to delete BlogComment : {}", id);
        blogCommentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
