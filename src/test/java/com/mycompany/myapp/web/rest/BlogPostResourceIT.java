package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.domain.enumeration.Template;
import com.mycompany.myapp.repository.BlogPostRepository;
import com.mycompany.myapp.service.BlogPostService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BlogPostResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BlogPostResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Template DEFAULT_TEMPLATE = Template.THEDAVID;
    private static final Template UPDATED_TEMPLATE = Template.THEDOLIO;

    private static final String ENTITY_API_URL = "/api/blog-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Mock
    private BlogPostRepository blogPostRepositoryMock;

    @Mock
    private BlogPostService blogPostServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBlogPostMockMvc;

    private BlogPost blogPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPost createEntity(EntityManager em) {
        BlogPost blogPost = new BlogPost().title(DEFAULT_TITLE).text(DEFAULT_TEXT).dateTime(DEFAULT_DATE_TIME).template(DEFAULT_TEMPLATE);
        return blogPost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogPost createUpdatedEntity(EntityManager em) {
        BlogPost blogPost = new BlogPost().title(UPDATED_TITLE).text(UPDATED_TEXT).dateTime(UPDATED_DATE_TIME).template(UPDATED_TEMPLATE);
        return blogPost;
    }

    @BeforeEach
    public void initTest() {
        blogPost = createEntity(em);
    }

    @Test
    @Transactional
    void createBlogPost() throws Exception {
        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();
        // Create the BlogPost
        restBlogPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isCreated());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate + 1);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBlogPost.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testBlogPost.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testBlogPost.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    void createBlogPostWithExistingId() throws Exception {
        // Create the BlogPost with an existing ID
        blogPost.setId(1L);

        int databaseSizeBeforeCreate = blogPostRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogPostRepository.findAll().size();
        // set the field null
        blogPost.setTitle(null);

        // Create the BlogPost, which fails.

        restBlogPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isBadRequest());

        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogPostRepository.findAll().size();
        // set the field null
        blogPost.setText(null);

        // Create the BlogPost, which fails.

        restBlogPostMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isBadRequest());

        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBlogPosts() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get all the blogPostList
        restBlogPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBlogPostsWithEagerRelationshipsIsEnabled() throws Exception {
        when(blogPostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBlogPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(blogPostServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllBlogPostsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(blogPostServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restBlogPostMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(blogPostServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        // Get the blogPost
        restBlogPostMockMvc
            .perform(get(ENTITY_API_URL_ID, blogPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(blogPost.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBlogPost() throws Exception {
        // Get the blogPost
        restBlogPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Update the blogPost
        BlogPost updatedBlogPost = blogPostRepository.findById(blogPost.getId()).get();
        // Disconnect from session so that the updates on updatedBlogPost are not directly saved in db
        em.detach(updatedBlogPost);
        updatedBlogPost.title(UPDATED_TITLE).text(UPDATED_TEXT).dateTime(UPDATED_DATE_TIME).template(UPDATED_TEMPLATE);

        restBlogPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBlogPost.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBlogPost))
            )
            .andExpect(status().isOk());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogPost.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testBlogPost.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testBlogPost.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void putNonExistingBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, blogPost.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(blogPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBlogPostWithPatch() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Update the blogPost using partial update
        BlogPost partialUpdatedBlogPost = new BlogPost();
        partialUpdatedBlogPost.setId(blogPost.getId());

        partialUpdatedBlogPost.title(UPDATED_TITLE).dateTime(UPDATED_DATE_TIME);

        restBlogPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogPost))
            )
            .andExpect(status().isOk());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogPost.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testBlogPost.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testBlogPost.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    void fullUpdateBlogPostWithPatch() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();

        // Update the blogPost using partial update
        BlogPost partialUpdatedBlogPost = new BlogPost();
        partialUpdatedBlogPost.setId(blogPost.getId());

        partialUpdatedBlogPost.title(UPDATED_TITLE).text(UPDATED_TEXT).dateTime(UPDATED_DATE_TIME).template(UPDATED_TEMPLATE);

        restBlogPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBlogPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBlogPost))
            )
            .andExpect(status().isOk());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
        BlogPost testBlogPost = blogPostList.get(blogPostList.size() - 1);
        assertThat(testBlogPost.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBlogPost.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testBlogPost.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testBlogPost.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    void patchNonExistingBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, blogPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(blogPost))
            )
            .andExpect(status().isBadRequest());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBlogPost() throws Exception {
        int databaseSizeBeforeUpdate = blogPostRepository.findAll().size();
        blogPost.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBlogPostMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(blogPost)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BlogPost in the database
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBlogPost() throws Exception {
        // Initialize the database
        blogPostRepository.saveAndFlush(blogPost);

        int databaseSizeBeforeDelete = blogPostRepository.findAll().size();

        // Delete the blogPost
        restBlogPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, blogPost.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogPost> blogPostList = blogPostRepository.findAll();
        assertThat(blogPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
