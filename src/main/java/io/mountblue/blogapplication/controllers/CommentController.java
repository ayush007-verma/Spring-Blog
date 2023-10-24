package io.mountblue.blogapplication.controllers;


import io.mountblue.blogapplication.entities.Comment;
import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.repositories.CommentRepository;
import io.mountblue.blogapplication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    // get comments by id



    @GetMapping("/posts/{postId}/comments")
    public String ListCommentsByPostId(@PathVariable("postId") Long postId, Model model) {
        System.out.println(postId);
        List<Comment> commentsByPostId = commentRepository.findByPostId(postId);
        List<Comment> commentList = commentRepository.findAll();
        model.addAttribute("commentsByPostId", commentsByPostId);

        for(Comment c : commentList) {
            System.out.println(c.getEmail() + " " + c.getComment() + " " + c.getPost_id());

        }
        System.out.println(commentList.size());

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        String loggedInUsername = userRepository.findByEmail(loggedInUser).getUsername();
        model.addAttribute("loggedInUsername", userRepository.findByEmail(loggedInUser).getUsername());


        return "Comments/CommentsByPostId";
    }

    @GetMapping("/posts/{postId}/comments/new")
    public String createCommentByPostId(@PathVariable("postId") Long postId, Model model) {

        model.addAttribute("inputNewComment", new Comment());
        model.addAttribute("clickedPostId", postId);

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        String loggedInUsername = userRepository.findByEmail(loggedInUser).getUsername();
        model.addAttribute("loggedInUsername", userRepository.findByEmail(loggedInUser).getUsername());


        return "Comments/CreateComment";
    }

    @PostMapping("/posts/{postId}/comments/new")
    public String processComment(@PathVariable("postId") Long postId, @ModelAttribute("inputComment")Comment inputNewComment, Model model) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUsername = userRepository.findByEmail(loggedInUser).getUsername();

        inputNewComment.setEmail(loggedInUser);
        inputNewComment.setName(loggedInUsername);

        inputNewComment.setPost_id(postId);
        inputNewComment.setCreated_at(new Date());
        inputNewComment.setUpdated_at(new Date());

        model.addAttribute("inputNewComment", inputNewComment);
        commentRepository.save(inputNewComment);

        System.out.print("processComment called id passed is :- ");
        System.out.println(inputNewComment.getPost_id());
        System.out.println(inputNewComment.getComment());
        System.out.println(inputNewComment.getEmail());


        return "redirect:/posts/" + postId + "/comments";
//        return "redirect:/posts/";
    }

    @GetMapping("/posts/{postId}/comments/update/{commentId}")
    public String updateCommentById(@PathVariable("commentId") Long commentId, @PathVariable("postId") Long clickedPostId, Model model) {

        Optional<Comment> retrievedCommentById = commentRepository.findById(commentId);

        if (retrievedCommentById.isPresent()) {
            Comment retreivedComment = retrievedCommentById.get();
            model.addAttribute("retreivedComment", retreivedComment);
            model.addAttribute("clickedPostId", clickedPostId);
            System.out.println("retreivedComment :- ");
            System.out.println(retreivedComment.getId());
            System.out.println(retreivedComment.getName());
            System.out.println(retreivedComment.getComment());
        }

        return "Comments/UpdateComment";
    }


    @PostMapping("/posts/{postId}/comments/update/{commentId}")
    public String processUpdateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,@ModelAttribute("retreivedComment") Comment retreivedComment, Model model) {
        retreivedComment.setPost_id(postId);
        retreivedComment.setId(commentId);

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        String loggedInUsername = userRepository.findByEmail(loggedInUser).getUsername();


        retreivedComment.setName(loggedInUsername);
        retreivedComment.setEmail(loggedInUser);

        commentRepository.save(retreivedComment);
        System.out.println("*********************");
        System.out.println("updated  post :- ");
        System.out.println(retreivedComment.getId());
        System.out.println(retreivedComment.getName());
        System.out.println(retreivedComment.getComment());
        System.out.println(retreivedComment.getPost_id());
        System.out.println("$$$$$$$$$$$$$$$$$$$$");


        return "redirect:/posts/" + postId + "/comments";
    }
    // delete a post by id
    @GetMapping("/posts/{postId}/comments/delete/{commentId}")
    public String deletePostById(@PathVariable("commentId") Long Id, @PathVariable("postId") Long postId) {

        commentRepository.deleteById(Id);

        return "redirect:/posts/"+postId+"/comments";
    }

}
