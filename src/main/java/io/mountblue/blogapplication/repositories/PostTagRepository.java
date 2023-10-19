package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.PostTag;
import io.mountblue.blogapplication.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    boolean existsByPostIdAndTagId(Long postId, Long tagId);

    List<PostTag> findByPostId(Long postId);
}

