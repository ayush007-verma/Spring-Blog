package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long post_id);
}

