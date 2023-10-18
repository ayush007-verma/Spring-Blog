package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/posts")
    public String listPosts(Model model) {

        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        return "Posts/Posts";
    }


    // get post by id;
    @GetMapping("/posts/{Id}")
    public String retreivedPostById(@PathVariable Long Id, Model model) {

        Optional<Post> retrievedPostById = postRepository.findById(Id);

        if (retrievedPostById.isPresent()) {
            Post post = retrievedPostById.get();
            model.addAttribute("inputPost", post);

            System.out.println("retreived post :- ");
            System.out.println(post.getId());
            System.out.println(post.getAuthor());
            System.out.println(post.getExcerpt());

        }

        return "Posts/Post";
    }

}
