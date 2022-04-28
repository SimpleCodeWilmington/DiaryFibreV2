package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BlogComment;
import com.mycompany.myapp.repository.BlogCommentRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BlogCommentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlogCommentResourceIT {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/blog-comments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BlogCommentRepository blogCommentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogCommentMockMvc;

    private BlogComment blogComment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogComment createEntity(EntityManager em) {
        BlogComment blogComment = new BlogComment().comment(DEFAULT_COMMENT).dateTime(DEFAULT_DATE_TIME);
        return blogComment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogComment createUpdatedEntity(EntityManager em) {
        BlogComment blogComment = new BlogComment().comment(UPDATED_COMMENT).dateTime(UPDATED_DATE_TIME);
        return blogComment;
    }

    @BeforeEach
    public void initTest() {
        blogComment = createEntity(em);
    }

    @Test
    @Transactional
    void createBlogComment() throws Exception {
        int databaseSizeBeforeCreate = blogCommentRepository.findAll().size();
        // Create the BlogComment
        restBlogCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogComment)))
            .andExpect(status().isCreated());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeCreate + 1);
        BlogComment testBlogComment = blogCommentList.get(blogCommentList.size() - 1);
        assertThat(testBlogComment.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testBlogComment.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void createBlogCommentWithExistingId() throws Exception {
        // Create the BlogComment with an existing ID
        blogComment.setId(1L);

        int databaseSizeBeforeCreate = blogCommentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogComment)))
            .andExpect(status().isBadRequest());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogCommentRepository.findAll().size();
        // set the field null
        blogComment.setComment(null);

        // Create the BlogComment, which fails.

        restBlogCommentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogComment)))
            .andExpect(status().isBadRequest());

        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBlogComments() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        // Get all the blogCommentList
        restBlogCommentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogComment.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))));
    }

    @Test
    @Transactional
    void getBlogComment() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        // Get the blogComment
        restBlogCommentMockMvc
            .perform(get(ENTITY_API_URL_ID, blogComment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogComment.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingBlogComment() throws Exception {
        // Get the blogComment
        restBlogCommentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBlogComment() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();

        // Update the blogComment
        BlogComment updatedBlogComment = blogCommentRepository.findById(blogComment.getId()).get();
        // Disconnect from session so that the updates on updatedBlogComment are not directly saved in db
        em.detach(updatedBlogComment);
        updatedBlogComment.comment(UPDATED_COMMENT).dateTime(UPDATED_DATE_TIME);

        restBlogCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlogComment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBlogComment))
            )
            .andExpect(status().isOk());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
        BlogComment testBlogComment = blogCommentList.get(blogCommentList.size() - 1);
        assertThat(testBlogComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testBlogComment.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blogComment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogComment))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogComment))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogComment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlogCommentWithPatch() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();

        // Update the blogComment using partial update
        BlogComment partialUpdatedBlogComment = new BlogComment();
        partialUpdatedBlogComment.setId(blogComment.getId());

        partialUpdatedBlogComment.comment(UPDATED_COMMENT);

        restBlogCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogComment))
            )
            .andExpect(status().isOk());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
        BlogComment testBlogComment = blogCommentList.get(blogCommentList.size() - 1);
        assertThat(testBlogComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testBlogComment.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateBlogCommentWithPatch() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();

        // Update the blogComment using partial update
        BlogComment partialUpdatedBlogComment = new BlogComment();
        partialUpdatedBlogComment.setId(blogComment.getId());

        partialUpdatedBlogComment.comment(UPDATED_COMMENT).dateTime(UPDATED_DATE_TIME);

        restBlogCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogComment))
            )
            .andExpect(status().isOk());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
        BlogComment testBlogComment = blogCommentList.get(blogCommentList.size() - 1);
        assertThat(testBlogComment.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testBlogComment.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blogComment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogComment))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogComment))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlogComment() throws Exception {
        int databaseSizeBeforeUpdate = blogCommentRepository.findAll().size();
        blogComment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogCommentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(blogComment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogComment in the database
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlogComment() throws Exception {
        // Initialize the database
        blogCommentRepository.saveAndFlush(blogComment);

        int databaseSizeBeforeDelete = blogCommentRepository.findAll().size();

        // Delete the blogComment
        restBlogCommentMockMvc
            .perform(delete(ENTITY_API_URL_ID, blogComment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogComment> blogCommentList = blogCommentRepository.findAll();
        assertThat(blogCommentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
