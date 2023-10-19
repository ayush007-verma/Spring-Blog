package io.mountblue.blogapplication.services;

import io.mountblue.blogapplication.entities.Tag;
import io.mountblue.blogapplication.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

//@Se/rvice
//public class TagService {
//    private TagRepository tagRepository;
//
//    public List<Tag> ListTagsByPostId(Long postId) {
//        return tagRepository.findByPostId(postId);
//    }
//}
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getTagsForPost(Long postId) {
        return tagRepository.findByPostId(postId);
    }

}