package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.repository.BlogPostRepository;
import com.mycompany.myapp.service.BlogPostService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BlogPost}.
 */
@Service
@Transactional
public class BlogPostServiceImpl implements BlogPostService {

    private final Logger log = LoggerFactory.getLogger(BlogPostServiceImpl.class);

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public BlogPost save(BlogPost blogPost) {
        log.debug("Request to save BlogPost : {}", blogPost);
        return blogPostRepository.save(blogPost);
    }

    @Override
    public BlogPost update(BlogPost blogPost) {
        log.debug("Request to save BlogPost : {}", blogPost);
        return blogPostRepository.save(blogPost);
    }

    @Override
    public Optional<BlogPost> partialUpdate(BlogPost blogPost) {
        log.debug("Request to partially update BlogPost : {}", blogPost);

        return blogPostRepository
            .findById(blogPost.getId())
            .map(existingBlogPost -> {
                if (blogPost.getTitle() != null) {
                    existingBlogPost.setTitle(blogPost.getTitle());
                }
                if (blogPost.getText() != null) {
                    existingBlogPost.setText(blogPost.getText());
                }
                if (blogPost.getDateTime() != null) {
                    existingBlogPost.setDateTime(blogPost.getDateTime());
                }
                if (blogPost.getTemplate() != null) {
                    existingBlogPost.setTemplate(blogPost.getTemplate());
                }

                return existingBlogPost;
            })
            .map(blogPostRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BlogPost> findAll(Pageable pageable) {
        log.debug("Request to get all BlogPosts");
        return blogPostRepository.findAll(pageable);
    }

    public Page<BlogPost> findAllWithEagerRelationships(Pageable pageable) {
        return blogPostRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogPost> findOne(Long id) {
        log.debug("Request to get BlogPost : {}", id);
        return blogPostRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogPost : {}", id);
        blogPostRepository.deleteById(id);
    }
}
