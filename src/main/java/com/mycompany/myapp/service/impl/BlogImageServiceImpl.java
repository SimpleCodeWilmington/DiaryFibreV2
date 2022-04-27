package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.BlogImage;
import com.mycompany.myapp.repository.BlogImageRepository;
import com.mycompany.myapp.service.BlogImageService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BlogImage}.
 */
@Service
@Transactional
public class BlogImageServiceImpl implements BlogImageService {

    private final Logger log = LoggerFactory.getLogger(BlogImageServiceImpl.class);

    private final BlogImageRepository blogImageRepository;

    public BlogImageServiceImpl(BlogImageRepository blogImageRepository) {
        this.blogImageRepository = blogImageRepository;
    }

    @Override
    public BlogImage save(BlogImage blogImage) {
        log.debug("Request to save BlogImage : {}", blogImage);
        return blogImageRepository.save(blogImage);
    }

    @Override
    public BlogImage update(BlogImage blogImage) {
        log.debug("Request to save BlogImage : {}", blogImage);
        return blogImageRepository.save(blogImage);
    }

    @Override
    public Optional<BlogImage> partialUpdate(BlogImage blogImage) {
        log.debug("Request to partially update BlogImage : {}", blogImage);

        return blogImageRepository
            .findById(blogImage.getId())
            .map(existingBlogImage -> {
                if (blogImage.getBlogPostID() != null) {
                    existingBlogImage.setBlogPostID(blogImage.getBlogPostID());
                }
                if (blogImage.getBlogImage() != null) {
                    existingBlogImage.setBlogImage(blogImage.getBlogImage());
                }
                if (blogImage.getBlogImageContentType() != null) {
                    existingBlogImage.setBlogImageContentType(blogImage.getBlogImageContentType());
                }
                if (blogImage.getImageNumber() != null) {
                    existingBlogImage.setImageNumber(blogImage.getImageNumber());
                }

                return existingBlogImage;
            })
            .map(blogImageRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogImage> findAll() {
        log.debug("Request to get all BlogImages");
        return blogImageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogImage> findOne(Long id) {
        log.debug("Request to get BlogImage : {}", id);
        return blogImageRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogImage : {}", id);
        blogImageRepository.deleteById(id);
    }
}
