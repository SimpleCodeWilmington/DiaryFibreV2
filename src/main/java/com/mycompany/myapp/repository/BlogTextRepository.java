package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogText;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BlogText entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogTextRepository extends JpaRepository<BlogText, Long> {}
