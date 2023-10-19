package io.mountblue.blogapplication.services;

import io.mountblue.blogapplication.entities.PostTag;
import io.mountblue.blogapplication.repositories.PostTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTagService {
    private final PostTagRepository postTagRepository;

    public PostTagService(PostTagRepository postTagRepository) {
        this.postTagRepository = postTagRepository;
    }

    public List<PostTag> getTagsForPost(Long postId) {
        return postTagRepository.findByPostId(postId);
    }

}