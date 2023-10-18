package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


            return "Posts/Post";
        }
        return "Posts/Posts";
    }

    // edit post by id
    @GetMapping("/posts/update/{Id}")
    public String updatePostById(@PathVariable Long Id,  Model model) {

        Optional<Post> retrievedPostById = postRepository.findById(Id);

        if (retrievedPostById.isPresent()) {
            Post retreivedPost = retrievedPostById.get();
            model.addAttribute("retreivedPost", retreivedPost);

            System.out.println("retreived post :- ");
            System.out.println(retreivedPost.getId());
            System.out.println(retreivedPost.getAuthor());
            System.out.println(retreivedPost.getExcerpt());
        }

        return "Posts/UpdatePost";
    }


    // update the post by id
    @PostMapping("/posts/update/{id}")
    public String processUpdatePost(@ModelAttribute("retreivedPost") Post retreivedPost, Model model) {

        postRepository.save(retreivedPost);

        System.out.println("updated  post :- ");
        System.out.println(retreivedPost.getId());
        System.out.println(retreivedPost.getAuthor());
        System.out.println(retreivedPost.getPublished_at());


        return "redirect:/posts";
    }


    @GetMapping("/posts/delete/{Id}")
    public String deletePostById(@PathVariable("Id") Long Id) {

        postRepository.deleteById(Id);

        return "redirect:/posts";
    }





}
