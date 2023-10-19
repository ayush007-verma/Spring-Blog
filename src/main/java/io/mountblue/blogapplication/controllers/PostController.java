package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.entities.Tag;
import io.mountblue.blogapplication.repositories.PostRepository;
import io.mountblue.blogapplication.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;


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

            List<Tag> tags = tagService.getTagsForPost(Id);
            model.addAttribute("tags", tags);

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

            List<Tag> inputTags = tagService.getTagsForPost(Id);
            model.addAttribute("inputTags", inputTags);

            System.out.println("Existing tags list : ");
            for(Tag tag : inputTags) {
                System.out.println(tag.getId() + " " + tag.getName() + " " + tag.getCreated_at() + " " + tag.getUpdated_at() + " " + tag.getPostId());
            }


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

    // delete a post by id
    @GetMapping("/posts/delete/{Id}")
    public String deletePostById(@PathVariable("Id") Long Id) {

        postRepository.deleteById(Id);

        return "redirect:/posts";
    }

    // create a post ...

    @GetMapping("/posts/new")
    public String createPost(Model model) {
        model.addAttribute("inputPost", new Post());
        String inputTags = "";
        model.addAttribute("inputTags", inputTags);

        return "Posts/CreatePost";
    }

    // process create-post request

    @PostMapping("/posts/new")
    public String processCreatePost(@ModelAttribute("inputPost") Post inputPost, @ModelAttribute("inputTags") String inputTags, RedirectAttributes redirectAttributes, Model model) {

        Post savedPost =  postRepository.save(inputPost);
        System.out.println("inputTags value : " + inputTags);
        System.out.println("postId in post Controller : " + savedPost.getId());

        redirectAttributes.addFlashAttribute("inputTags", inputTags); // Add inputags as flash attribute
        redirectAttributes.addFlashAttribute("postId", savedPost.getId());
        return "redirect:/posts/tags/new";
    }


}
