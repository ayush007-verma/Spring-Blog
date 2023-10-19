package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

}
