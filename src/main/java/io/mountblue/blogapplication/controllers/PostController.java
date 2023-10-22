package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.entities.PostTag;
import io.mountblue.blogapplication.entities.Tag;
import io.mountblue.blogapplication.repositories.PostRepository;
import io.mountblue.blogapplication.repositories.TagRepository;
import io.mountblue.blogapplication.services.PostTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    private PostTagService postTagService;

    @Autowired
    private TagRepository tagRepository;


//    @GetMapping("/posts")
//    public String listPosts(Model model, @RequestParam(defaultValue = "1") int page) {
//        int pageSize = 6;
//
//        List<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
//
//        return "Posts/Posts";
//    }

    @GetMapping("/posts")
    public String listPosts(Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 6);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.getContent();

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", postPage.getNumber() + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());

        return "Posts/Posts";
    }

    // get post by id;
    @GetMapping("/posts/{Id}")
    public String retreivedPostById(@PathVariable Long Id, Model model) {

        Optional<Post> retrievedPostById = postRepository.findById(Id);

        for(Tag t : tagRepository.findAll()) {
            System.out.println(t.getName());
        }

        if (retrievedPostById.isPresent()) {
            Post post = retrievedPostById.get();
            model.addAttribute("inputPost", post);


            System.out.println("retreived post :- ");
            System.out.println(post.getId());
            System.out.println(post.getAuthor());
            System.out.println(post.getExcerpt());

//            List<PostTag> tags = postTagService.getTagsForPost(Id);
//            model.addAttribute("tags", tags);

            List<PostTag> inputPostTags = postTagService.getTagsForPost(Id);
            List<Tag> inputTags = new ArrayList<>();

            for(PostTag postTag : inputPostTags) {
                inputTags.add(tagRepository.findById(postTag.getTagId()).get());
            }

            model.addAttribute("tags", inputTags);


            return "Posts/Post";
        }
        return "Posts/Posts";
    }

    // edit post by id
    @GetMapping("/posts/update/{Id}")
    public String updatePostById(@PathVariable Long Id, Model model) {

        Optional<Post> retrievedPostById = postRepository.findById(Id);

        if (retrievedPostById.isPresent()) {
            Post retreivedPost = retrievedPostById.get();
            model.addAttribute("retreivedPost", retreivedPost);

            List<PostTag> inputPostTags = postTagService.getTagsForPost(Id);
            String inputTags = "";

            for(PostTag postTag : inputPostTags) {
               inputTags += (tagRepository.findById(postTag.getTagId()).get().getName());
               inputTags += ",";
            }

            model.addAttribute("inputTags", inputTags);



            System.out.println("Existing tags list : ");
            System.out.print(inputTags);
//            for (Tag tag : inputTags) {
//                System.out.println(tag.getId() + " " + tag.getName() + " " + tag.getCreated_at() + " " + tag.getUpdated_at());
//            }


            System.out.println("retreived post :- ");
            System.out.println(retreivedPost.getId());
            System.out.println(retreivedPost.getAuthor());
            System.out.println(retreivedPost.getExcerpt());
        }

        return "Posts/UpdatePost";
    }


    // update the post by id
    @PostMapping("/posts/update/{id}")
    public String processUpdatePost(@ModelAttribute("retreivedPost") Post retreivedPost, @ModelAttribute("inputTags") String inputTags,RedirectAttributes redirectAttributes, Model model) {

        postRepository.save(retreivedPost);

        System.out.println("Post Request tags list : ");
        System.out.println(inputTags);

//        for (Tag tag : inputTags) {
//            System.out.println(tag.getId() + " " + tag.getName() + " " + tag.getCreated_at() + " " + tag.getUpdated_at());
//        }

//        System.out.println("updated  post :- ");
//        System.out.println(retreivedPost.getId());
//        System.out.println(retreivedPost.getAuthor());
//        System.out.println(retreivedPost.getPublished_at());


        redirectAttributes.addFlashAttribute("inputTags", inputTags); // Add inputags as flash attribute
        redirectAttributes.addFlashAttribute("postId", retreivedPost.getId());
        return "redirect:/posts/tags/update";
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

        Post savedPost = postRepository.save(inputPost);
        System.out.println("inputTags value : " + inputTags);
        System.out.println("postId in post Controller : " + savedPost.getId());

        redirectAttributes.addFlashAttribute("inputTags", inputTags); // Add inputags as flash attribute
        redirectAttributes.addFlashAttribute("postId", savedPost.getId());
        return "redirect:/posts/tags/new";
    }


    // search a post

    @GetMapping("/posts/search")
    public String searchPost( Model model, Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber(), 6);

        List<Post> posts = new ArrayList<>();

        Page<Post> postPage = new PageImpl<>(posts, pageable, 0);

        model.addAttribute("posts", posts);
        model.addAttribute("query1", "X");
        model.addAttribute("sortQuery", "");
        model.addAttribute("filterQuery", "");
        model.addAttribute("filterValue", "");

        model.addAttribute("currentPage", postPage.getNumber() + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "Posts/SearchPost";
    }

    @PostMapping("/posts/search")
    public String processSearchPost(@ModelAttribute("query1") String query, Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), 6);
        System.out.println("Qyert :-> '" + query + "'(");

        Page<Post> postPage = postRepository.searchPosts(query, pageable);

        List<Post> posts = postPage.getContent();
        System.out.println(posts.size());
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", postPage.getNumber() + 1);
        model.addAttribute("totalPages", postPage.getTotalPages());

        return "Posts/SearchPost";
    }

    @PostMapping("/posts/search/sort")
    public String sortListPosts(@ModelAttribute("sortQuery") String sortQuery, @ModelAttribute("query1") String searchQuery, @ModelAttribute("filterQuery") String filterName, @ModelAttribute("filterValue") String filterValue, Model model, Pageable pageable) {
        System.out.println("**********************");
//        searchQuery = "ML";

        System.out.println("search Query :-> '" + searchQuery + "'");
        System.out.println("sortQuery :-> '" + sortQuery + "'(");
        System.out.println("filterName :-> '" + filterName + "'(");
        System.out.println("filterValue :-> '" + filterValue + "'(");

        String orderQuery = "ASC";
//        String filterName = "author";
//        String filterValue = "Ryan";

        pageable = PageRequest.of(pageable.getPageNumber(), 6, orderQuery.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortQuery);

        Page<Post> postPage = postRepository.searchPosts(searchQuery, pageable);
//        Page<Post> postPage = postRepository.searchAndFilterPosts(searchQuery, filterName, filterValue, pageable);
        System.out.println(postPage);
        List<Post> posts = postPage.getContent();

        System.out.println(posts.size());
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", postPage.getNumber() + 1);
        model   .addAttribute("totalPages", postPage.getTotalPages());


        return "Posts/SearchPost";
    }


    @PostMapping("/posts/search/filter")
    public String filterListPosts(@ModelAttribute("filterQuery") String filterName, @ModelAttribute("filterValue") String filterValue, @ModelAttribute("sortQuery") String sortQuery, @ModelAttribute("query1") String searchQuery ,Model model, Pageable pageable) {
        System.out.println("**********************");
//        searchQuery = "ML";
//        String sortQuery = "Id";

        System.out.println("search Query :-> '" + searchQuery + "'");
        System.out.println("sortQuery :-> '" + sortQuery + "'(");
        System.out.println("filterName :-> '" + filterName + "'(");
        System.out.println("filterValue :-> '" + filterValue + "'(");

        String orderQuery = "ASC";
//        String filterName = "author";
//        String filterValue = "Ryan";

        pageable = PageRequest.of(pageable.getPageNumber(), 6);//, orderQuery.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortQuery);

//        Page<Post> postPage = postRepository.searchAndFilterPosts(searchQuery, filterName, filterValue, pageable);
        Page<Post> postPage = postRepository.searchPosts(searchQuery, pageable);

        System.out.println(postPage);
        List<Post> posts = postPage.getContent();

        System.out.println(posts.size());
        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", postPage.getNumber() + 1);
        model   .addAttribute("totalPages", postPage.getTotalPages());


        return "Posts/SearchPost";
    }



}