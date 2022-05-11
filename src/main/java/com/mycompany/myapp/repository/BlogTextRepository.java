package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogPost;
import com.mycompany.myapp.domain.BlogText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the BlogText entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogTextRepository extends JpaRepository<BlogText, Long> {

    //This used to be blank

    //List<BlogText> findByBlogUserLoginOrderByDateTimeDesc(String currentUserLogin);
    //Page<BlogPost> findByBlogUserLoginOrderByDateTimeDesc(String currentUserLogin, Pageable pageable);

}
