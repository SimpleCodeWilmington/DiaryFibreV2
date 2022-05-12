package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BlogImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogImageRepository extends JpaRepository<BlogImage, Long> {



}
