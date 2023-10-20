package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN PostTag pt ON p.Id = pt.postId JOIN Tag t ON pt.tagId = t.Id " +
            "WHERE p.title LIKE %:query% OR p.content LIKE %:query% OR p.author LIKE %:query% " +
            "OR p.excerpt LIKE %:query% OR t.name LIKE %:query%")
    Page<Post> searchPosts(@Param("query") String query, Pageable pageable);


    @Query("SELECT p FROM Post p JOIN PostTag pt ON p.Id = pt.postId JOIN Tag t ON pt.tagId = t.Id WHERE " +
            "(p.title LIKE %:searchQuery% OR p.content LIKE %:searchQuery% OR p.author LIKE %:searchQuery% OR p.excerpt LIKE %:searchQuery% OR t.name LIKE %:searchQuery%) " +
            "AND (:filterName IS NULL OR " +
            "(:filterName = 'title' AND p.title LIKE %:filterValue%) OR " +
            "(:filterName = 'content' AND p.content LIKE %:filterValue%) OR " +
            "(:filterName = 'author' AND p.author LIKE %:filterValue%) OR " +
            "(:filterName = 'tag' AND t.name LIKE %:filterValue%))")
    Page<Post> searchAndFilterPosts(
            @Param("searchQuery") String searchQuery,
            @Param("filterName") String filterName,
            @Param("filterValue") String filterValue,
            Pageable pageable);

}
