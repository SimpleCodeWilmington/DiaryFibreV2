package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BlogImage;
import com.mycompany.myapp.repository.BlogImageRepository;
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
 * Integration tests for the {@link BlogImageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BlogImageResourceIT {

    private static final byte[] DEFAULT_BLOG_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOG_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BLOG_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOG_IMAGE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_IMAGE_NUMBER = 1;
    private static final Integer UPDATED_IMAGE_NUMBER = 2;

    private static final String ENTITY_API_URL = "/api/blog-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BlogImageRepository blogImageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogImageMockMvc;

    private BlogImage blogImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogImage createEntity(EntityManager em) {
        BlogImage blogImage = new BlogImage()
            .blogImage(DEFAULT_BLOG_IMAGE)
            .blogImageContentType(DEFAULT_BLOG_IMAGE_CONTENT_TYPE)
            .imageNumber(DEFAULT_IMAGE_NUMBER);
        return blogImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogImage createUpdatedEntity(EntityManager em) {
        BlogImage blogImage = new BlogImage()
            .blogImage(UPDATED_BLOG_IMAGE)
            .blogImageContentType(UPDATED_BLOG_IMAGE_CONTENT_TYPE)
            .imageNumber(UPDATED_IMAGE_NUMBER);
        return blogImage;
    }

    @BeforeEach
    public void initTest() {
        blogImage = createEntity(em);
    }

    @Test
    @Transactional
    void createBlogImage() throws Exception {
        int databaseSizeBeforeCreate = blogImageRepository.findAll().size();
        // Create the BlogImage
        restBlogImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogImage)))
            .andExpect(status().isCreated());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeCreate + 1);
        BlogImage testBlogImage = blogImageList.get(blogImageList.size() - 1);
        assertThat(testBlogImage.getBlogImage()).isEqualTo(DEFAULT_BLOG_IMAGE);
        assertThat(testBlogImage.getBlogImageContentType()).isEqualTo(DEFAULT_BLOG_IMAGE_CONTENT_TYPE);
        assertThat(testBlogImage.getImageNumber()).isEqualTo(DEFAULT_IMAGE_NUMBER);
    }

    @Test
    @Transactional
    void createBlogImageWithExistingId() throws Exception {
        // Create the BlogImage with an existing ID
        blogImage.setId(1L);

        int databaseSizeBeforeCreate = blogImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogImage)))
            .andExpect(status().isBadRequest());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkImageNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogImageRepository.findAll().size();
        // set the field null
        blogImage.setImageNumber(null);

        // Create the BlogImage, which fails.

        restBlogImageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogImage)))
            .andExpect(status().isBadRequest());

        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBlogImages() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        // Get all the blogImageList
        restBlogImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].blogImageContentType").value(hasItem(DEFAULT_BLOG_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blogImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOG_IMAGE))))
            .andExpect(jsonPath("$.[*].imageNumber").value(hasItem(DEFAULT_IMAGE_NUMBER)));
    }

    @Test
    @Transactional
    void getBlogImage() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        // Get the blogImage
        restBlogImageMockMvc
            .perform(get(ENTITY_API_URL_ID, blogImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogImage.getId().intValue()))
            .andExpect(jsonPath("$.blogImageContentType").value(DEFAULT_BLOG_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.blogImage").value(Base64Utils.encodeToString(DEFAULT_BLOG_IMAGE)))
            .andExpect(jsonPath("$.imageNumber").value(DEFAULT_IMAGE_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingBlogImage() throws Exception {
        // Get the blogImage
        restBlogImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBlogImage() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();

        // Update the blogImage
        BlogImage updatedBlogImage = blogImageRepository.findById(blogImage.getId()).get();
        // Disconnect from session so that the updates on updatedBlogImage are not directly saved in db
        em.detach(updatedBlogImage);
        updatedBlogImage
            .blogImage(UPDATED_BLOG_IMAGE)
            .blogImageContentType(UPDATED_BLOG_IMAGE_CONTENT_TYPE)
            .imageNumber(UPDATED_IMAGE_NUMBER);

        restBlogImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlogImage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBlogImage))
            )
            .andExpect(status().isOk());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
        BlogImage testBlogImage = blogImageList.get(blogImageList.size() - 1);
        assertThat(testBlogImage.getBlogImage()).isEqualTo(UPDATED_BLOG_IMAGE);
        assertThat(testBlogImage.getBlogImageContentType()).isEqualTo(UPDATED_BLOG_IMAGE_CONTENT_TYPE);
        assertThat(testBlogImage.getImageNumber()).isEqualTo(UPDATED_IMAGE_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blogImage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogImage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlogImageWithPatch() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();

        // Update the blogImage using partial update
        BlogImage partialUpdatedBlogImage = new BlogImage();
        partialUpdatedBlogImage.setId(blogImage.getId());

        partialUpdatedBlogImage.imageNumber(UPDATED_IMAGE_NUMBER);

        restBlogImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogImage))
            )
            .andExpect(status().isOk());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
        BlogImage testBlogImage = blogImageList.get(blogImageList.size() - 1);
        assertThat(testBlogImage.getBlogImage()).isEqualTo(DEFAULT_BLOG_IMAGE);
        assertThat(testBlogImage.getBlogImageContentType()).isEqualTo(DEFAULT_BLOG_IMAGE_CONTENT_TYPE);
        assertThat(testBlogImage.getImageNumber()).isEqualTo(UPDATED_IMAGE_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateBlogImageWithPatch() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();

        // Update the blogImage using partial update
        BlogImage partialUpdatedBlogImage = new BlogImage();
        partialUpdatedBlogImage.setId(blogImage.getId());

        partialUpdatedBlogImage
            .blogImage(UPDATED_BLOG_IMAGE)
            .blogImageContentType(UPDATED_BLOG_IMAGE_CONTENT_TYPE)
            .imageNumber(UPDATED_IMAGE_NUMBER);

        restBlogImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogImage))
            )
            .andExpect(status().isOk());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
        BlogImage testBlogImage = blogImageList.get(blogImageList.size() - 1);
        assertThat(testBlogImage.getBlogImage()).isEqualTo(UPDATED_BLOG_IMAGE);
        assertThat(testBlogImage.getBlogImageContentType()).isEqualTo(UPDATED_BLOG_IMAGE_CONTENT_TYPE);
        assertThat(testBlogImage.getImageNumber()).isEqualTo(UPDATED_IMAGE_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blogImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogImage))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlogImage() throws Exception {
        int databaseSizeBeforeUpdate = blogImageRepository.findAll().size();
        blogImage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogImageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(blogImage))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogImage in the database
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlogImage() throws Exception {
        // Initialize the database
        blogImageRepository.saveAndFlush(blogImage);

        int databaseSizeBeforeDelete = blogImageRepository.findAll().size();

        // Delete the blogImage
        restBlogImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, blogImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogImage> blogImageList = blogImageRepository.findAll();
        assertThat(blogImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
