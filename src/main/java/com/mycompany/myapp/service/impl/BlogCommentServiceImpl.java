package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.BlogComment;
import com.mycompany.myapp.repository.BlogCommentRepository;
import com.mycompany.myapp.service.BlogCommentService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BlogComment}.
 */
@Service
@Transactional
public class BlogCommentServiceImpl implements BlogCommentService {

    private final Logger log = LoggerFactory.getLogger(BlogCommentServiceImpl.class);

    private final BlogCommentRepository blogCommentRepository;

    public BlogCommentServiceImpl(BlogCommentRepository blogCommentRepository) {
        this.blogCommentRepository = blogCommentRepository;
    }

    @Override
    public BlogComment save(BlogComment blogComment) {
        log.debug("Request to save BlogComment : {}", blogComment);
        return blogCommentRepository.save(blogComment);
    }

    @Override
    public BlogComment update(BlogComment blogComment) {
        log.debug("Request to save BlogComment : {}", blogComment);
        return blogCommentRepository.save(blogComment);
    }

    @Override
    public Optional<BlogComment> partialUpdate(BlogComment blogComment) {
        log.debug("Request to partially update BlogComment : {}", blogComment);

        return blogCommentRepository
            .findById(blogComment.getId())
            .map(existingBlogComment -> {
                if (blogComment.getComment() != null) {
                    existingBlogComment.setComment(blogComment.getComment());
                }
                if (blogComment.getDateTime() != null) {
                    existingBlogComment.setDateTime(blogComment.getDateTime());
                }

                return existingBlogComment;
            })
            .map(blogCommentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogComment> findAll() {
        log.debug("Request to get all BlogComments");
        return blogCommentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogComment> findOne(Long id) {
        log.debug("Request to get BlogComment : {}", id);
        return blogCommentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogComment : {}", id);
        blogCommentRepository.deleteById(id);
    }
}
