package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BlogComment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BlogComment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
    @Query("select blogComment from BlogComment blogComment where blogComment.user.login = ?#{principal.username}")
    List<BlogComment> findByUserIsCurrentUser();
}
