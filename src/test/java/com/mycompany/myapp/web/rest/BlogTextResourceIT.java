package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BlogText;
import com.mycompany.myapp.repository.BlogTextRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link BlogTextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlogTextResourceIT {

    private static final byte[] DEFAULT_BLOG_TEXT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOG_TEXT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOG_TEXT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOG_TEXT_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/blog-texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BlogTextRepository blogTextRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogTextMockMvc;

    private BlogText blogText;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogText createEntity(EntityManager em) {
        BlogText blogText = new BlogText().blogText(DEFAULT_BLOG_TEXT).blogTextContentType(DEFAULT_BLOG_TEXT_CONTENT_TYPE);
        return blogText;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogText createUpdatedEntity(EntityManager em) {
        BlogText blogText = new BlogText().blogText(UPDATED_BLOG_TEXT).blogTextContentType(UPDATED_BLOG_TEXT_CONTENT_TYPE);
        return blogText;
    }

    @BeforeEach
    public void initTest() {
        blogText = createEntity(em);
    }

    @Test
    @Transactional
    void createBlogText() throws Exception {
        int databaseSizeBeforeCreate = blogTextRepository.findAll().size();
        // Create the BlogText
        restBlogTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogText)))
            .andExpect(status().isCreated());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeCreate + 1);
        BlogText testBlogText = blogTextList.get(blogTextList.size() - 1);
        assertThat(testBlogText.getBlogText()).isEqualTo(DEFAULT_BLOG_TEXT);
        assertThat(testBlogText.getBlogTextContentType()).isEqualTo(DEFAULT_BLOG_TEXT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createBlogTextWithExistingId() throws Exception {
        // Create the BlogText with an existing ID
        blogText.setId(1L);

        int databaseSizeBeforeCreate = blogTextRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogText)))
            .andExpect(status().isBadRequest());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBlogTexts() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        // Get all the blogTextList
        restBlogTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogText.getId().intValue())))
            .andExpect(jsonPath("$.[*].blogTextContentType").value(hasItem(DEFAULT_BLOG_TEXT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blogText").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOG_TEXT))));
    }

    @Test
    @Transactional
    void getBlogText() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        // Get the blogText
        restBlogTextMockMvc
            .perform(get(ENTITY_API_URL_ID, blogText.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogText.getId().intValue()))
            .andExpect(jsonPath("$.blogTextContentType").value(DEFAULT_BLOG_TEXT_CONTENT_TYPE))
            .andExpect(jsonPath("$.blogText").value(Base64Utils.encodeToString(DEFAULT_BLOG_TEXT)));
    }

    @Test
    @Transactional
    void getNonExistingBlogText() throws Exception {
        // Get the blogText
        restBlogTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBlogText() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();

        // Update the blogText
        BlogText updatedBlogText = blogTextRepository.findById(blogText.getId()).get();
        // Disconnect from session so that the updates on updatedBlogText are not directly saved in db
        em.detach(updatedBlogText);
        updatedBlogText.blogText(UPDATED_BLOG_TEXT).blogTextContentType(UPDATED_BLOG_TEXT_CONTENT_TYPE);

        restBlogTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlogText.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBlogText))
            )
            .andExpect(status().isOk());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
        BlogText testBlogText = blogTextList.get(blogTextList.size() - 1);
        assertThat(testBlogText.getBlogText()).isEqualTo(UPDATED_BLOG_TEXT);
        assertThat(testBlogText.getBlogTextContentType()).isEqualTo(UPDATED_BLOG_TEXT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blogText.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogText))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogText))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogText)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlogTextWithPatch() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();

        // Update the blogText using partial update
        BlogText partialUpdatedBlogText = new BlogText();
        partialUpdatedBlogText.setId(blogText.getId());

        restBlogTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogText))
            )
            .andExpect(status().isOk());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
        BlogText testBlogText = blogTextList.get(blogTextList.size() - 1);
        assertThat(testBlogText.getBlogText()).isEqualTo(DEFAULT_BLOG_TEXT);
        assertThat(testBlogText.getBlogTextContentType()).isEqualTo(DEFAULT_BLOG_TEXT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateBlogTextWithPatch() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();

        // Update the blogText using partial update
        BlogText partialUpdatedBlogText = new BlogText();
        partialUpdatedBlogText.setId(blogText.getId());

        partialUpdatedBlogText.blogText(UPDATED_BLOG_TEXT).blogTextContentType(UPDATED_BLOG_TEXT_CONTENT_TYPE);

        restBlogTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogText))
            )
            .andExpect(status().isOk());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
        BlogText testBlogText = blogTextList.get(blogTextList.size() - 1);
        assertThat(testBlogText.getBlogText()).isEqualTo(UPDATED_BLOG_TEXT);
        assertThat(testBlogText.getBlogTextContentType()).isEqualTo(UPDATED_BLOG_TEXT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blogText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogText))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogText))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlogText() throws Exception {
        int databaseSizeBeforeUpdate = blogTextRepository.findAll().size();
        blogText.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogTextMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(blogText)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogText in the database
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlogText() throws Exception {
        // Initialize the database
        blogTextRepository.saveAndFlush(blogText);

        int databaseSizeBeforeDelete = blogTextRepository.findAll().size();

        // Delete the blogText
        restBlogTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, blogText.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogText> blogTextList = blogTextRepository.findAll();
        assertThat(blogTextList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
