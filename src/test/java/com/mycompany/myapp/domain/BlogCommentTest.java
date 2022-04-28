package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlogCommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogComment.class);
        BlogComment blogComment1 = new BlogComment();
        blogComment1.setId(1L);
        BlogComment blogComment2 = new BlogComment();
        blogComment2.setId(blogComment1.getId());
        assertThat(blogComment1).isEqualTo(blogComment2);
        blogComment2.setId(2L);
        assertThat(blogComment1).isNotEqualTo(blogComment2);
        blogComment1.setId(null);
        assertThat(blogComment1).isNotEqualTo(blogComment2);
    }
}
