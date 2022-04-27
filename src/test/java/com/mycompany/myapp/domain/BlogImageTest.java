package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlogImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogImage.class);
        BlogImage blogImage1 = new BlogImage();
        blogImage1.setId(1L);
        BlogImage blogImage2 = new BlogImage();
        blogImage2.setId(blogImage1.getId());
        assertThat(blogImage1).isEqualTo(blogImage2);
        blogImage2.setId(2L);
        assertThat(blogImage1).isNotEqualTo(blogImage2);
        blogImage1.setId(null);
        assertThat(blogImage1).isNotEqualTo(blogImage2);
    }
}
