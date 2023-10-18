package io.mountblue.blogapplication.controllers;


import io.mountblue.blogapplication.entities.Comment;
import io.mountblue.blogapplication.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

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
        return "Comments/CommentsByPostId";
    }

    @GetMapping("/posts/{postId}/comments/new")
    public String createCommentByPostId(@PathVariable("postId") Long postId, Model model) {

        model.addAttribute("inputNewComment", new Comment());
        model.addAttribute("clickedPostId", postId);

        return "Comments/CreateComment";
    }

    @PostMapping("/posts/{postId}/comments/new")
    public String processComment(@PathVariable("postId") Long postId, @ModelAttribute("inputComment")Comment inputNewComment, Model model) {
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




}
