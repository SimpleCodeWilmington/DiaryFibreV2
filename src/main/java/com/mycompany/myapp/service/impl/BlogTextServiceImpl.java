package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.BlogText;
import com.mycompany.myapp.repository.BlogTextRepository;
import com.mycompany.myapp.service.BlogTextService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BlogText}.
 */
@Service
@Transactional
public class BlogTextServiceImpl implements BlogTextService {

    private final Logger log = LoggerFactory.getLogger(BlogTextServiceImpl.class);

    private final BlogTextRepository blogTextRepository;

    public BlogTextServiceImpl(BlogTextRepository blogTextRepository) {
        this.blogTextRepository = blogTextRepository;
    }

    @Override
    public BlogText save(BlogText blogText) {
        log.debug("Request to save BlogText : {}", blogText);
        return blogTextRepository.save(blogText);
    }

    @Override
    public BlogText update(BlogText blogText) {
        log.debug("Request to save BlogText : {}", blogText);
        return blogTextRepository.save(blogText);
    }

    @Override
    public Optional<BlogText> partialUpdate(BlogText blogText) {
        log.debug("Request to partially update BlogText : {}", blogText);

        return blogTextRepository
            .findById(blogText.getId())
            .map(existingBlogText -> {
                if (blogText.getBlogPostID() != null) {
                    existingBlogText.setBlogPostID(blogText.getBlogPostID());
                }
                if (blogText.getBlogText() != null) {
                    existingBlogText.setBlogText(blogText.getBlogText());
                }
                if (blogText.getBlogTextContentType() != null) {
                    existingBlogText.setBlogTextContentType(blogText.getBlogTextContentType());
                }

                return existingBlogText;
            })
            .map(blogTextRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BlogText> findAll() {
        log.debug("Request to get all BlogTexts");
        return blogTextRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BlogText> findOne(Long id) {
        log.debug("Request to get BlogText : {}", id);
        return blogTextRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BlogText : {}", id);
        blogTextRepository.deleteById(id);
    }
}
