package io.mountblue.blogapplication.controllers;

import io.mountblue.blogapplication.entities.Post;
import io.mountblue.blogapplication.entities.Tag;
import io.mountblue.blogapplication.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/posts/tags/new")
    public String CreateTagsWithPostId(@ModelAttribute("inputTags") String inputTags, @ModelAttribute("postId") Long postId, Model model) {

        System.out.println("inputTags in TagsController : " +  inputTags);
        System.out.println("postId in tag controller : " + postId);

        String[] tagNames = inputTags.split(",");
        for(String tagName : tagNames) {
            Tag newTag = new Tag();

            newTag.setName(tagName);
            newTag.setCreated_at(new Date());
            newTag.setUpdated_at(new Date());
            newTag.setPostId(postId);


            tagRepository.save(newTag);

        }

        System.out.println("Tag repository :-> ");

        for(Tag tag : tagRepository.findAll()) {
            System.out.println(tag.getId() + " " + tag.getName() + " " + tag.getCreated_at() + " " + tag.getUpdated_at() + " " + tag.getPostId());
        }
        return "Tags/Tag";
    }


//    @GetMapping("/posts/tags/{postId}")
//    public String ListTagsWithPostId(@PathVariable("postId") Long postId, @ModelAttribute("inputPost") Post inputPost, Model model) {
//
//        List<Tag>  tags = tagRepository.findByPostId(postId);
//
//        model.addAttribute("inputPost", inputPost);
//        model.addAttribute("tags", tags);
//
//        return "redirect:/posts/" + postId;
//    }
}
