package io.mountblue.blogapplication.controllers.api;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestControllers {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/api/posts/{postId}")
    public Post getPostId(@PathVariable("postId") Long postId) {
        return postRepository.findById(postId).get();
    }

    @GetMapping("/api/posts/search")
    public Page<Post> searchPost(@RequestParam("searchQuery") String searchQuery, Pageable pageable) {
        return postRepository.searchPosts(searchQuery, pageable);
    }


    @GetMapping("/api/posts/search/filter")
    public List<Post> searchPost(Model model, @RequestParam("searchQuery") String searchQuery, @RequestParam("sortQuery") String sortQuery, @RequestParam("filterQuery") String filterQuery, @RequestParam("filterValue") String filterValue, @RequestParam("orderQuery") String orderQuery, Pageable pageable) {

        System.out.print("render /search");

        pageable = PageRequest.of(pageable.getPageNumber(), 6, orderQuery.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortQuery);

        Page<Post> postPage = postRepository.searchAndFilterPosts(searchQuery, filterQuery, filterValue, pageable);

        List<Post> posts = postPage.getContent();


        return posts;
    }

}
