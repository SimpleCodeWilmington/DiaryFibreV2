package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Blog;
import com.mycompany.myapp.repository.BlogRepository;
import com.mycompany.myapp.service.BlogService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Blog}.
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);

    private final BlogRepository blogRepository;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public Blog save(Blog blog) {
        log.debug("Request to save Blog : {}", blog);
        return blogRepository.save(blog);
    }

    @Override
    public Blog update(Blog blog) {
        log.debug("Request to save Blog : {}", blog);
        return blogRepository.save(blog);
    }

    @Override
    public Optional<Blog> partialUpdate(Blog blog) {
        log.debug("Request to partially update Blog : {}", blog);

        return blogRepository
            .findById(blog.getId())
            .map(existingBlog -> {
                if (blog.getBlogName() != null) {
                    existingBlog.setBlogName(blog.getBlogName());
                }
                if (blog.getBlogOwner() != null) {
                    existingBlog.setBlogOwner(blog.getBlogOwner());
                }
                if (blog.getTemplate() != null) {
                    existingBlog.setTemplate(blog.getTemplate());
                }
                if (blog.getAccessStatus() != null) {
                    existingBlog.setAccessStatus(blog.getAccessStatus());
                }

                return existingBlog;
            })
            .map(blogRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Blog> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        return blogRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Blog> findOne(Long id) {
        log.debug("Request to get Blog : {}", id);
        return blogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Blog : {}", id);
        blogRepository.deleteById(id);
    }
}
