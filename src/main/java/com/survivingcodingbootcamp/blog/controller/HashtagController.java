package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.storage.PostStorage;
import com.survivingcodingbootcamp.blog.storage.TopicStorage;
import com.survivingcodingbootcamp.blog.storage.repository.HashtagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HashtagController {
    private PostStorage postStorage;
    private TopicStorage topicStorage;
    private HashtagRepository hashtagRepo;
    private Long id;

    public HashtagController(PostStorage postStorage, TopicStorage topicStorage, HashtagRepository hashtagRepo) {
        this.postStorage = postStorage;
        this.topicStorage = topicStorage;
        this.hashtagRepo = hashtagRepo;
    }

    @RequestMapping("/hashtags")
    public String getHashtag(Model model){
        model.addAttribute("hashtags", hashtagRepo.findAll());

        return "all-hashtag-template";
    }
    @RequestMapping("/hashtags/{id}")
    public String singleHashtag(Model model, @PathVariable long id){
        model.addAttribute("hashtag",hashtagRepo.findById(id).get());

        return "hashtag-template";

    }

}
