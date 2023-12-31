package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String tagName);
}
