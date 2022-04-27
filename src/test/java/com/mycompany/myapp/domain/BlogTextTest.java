package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BlogTextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BlogText.class);
        BlogText blogText1 = new BlogText();
        blogText1.setId(1L);
        BlogText blogText2 = new BlogText();
        blogText2.setId(blogText1.getId());
        assertThat(blogText1).isEqualTo(blogText2);
        blogText2.setId(2L);
        assertThat(blogText1).isNotEqualTo(blogText2);
        blogText1.setId(null);
        assertThat(blogText1).isNotEqualTo(blogText2);
    }
}
