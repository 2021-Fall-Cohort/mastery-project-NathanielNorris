package com.survivingcodingbootcamp.blog.controller;

import com.survivingcodingbootcamp.blog.model.Hashtag;
import com.survivingcodingbootcamp.blog.model.Post;
import com.survivingcodingbootcamp.blog.model.Topic;
import com.survivingcodingbootcamp.blog.storage.PostStorage;
import com.survivingcodingbootcamp.blog.storage.TopicStorage;
import com.survivingcodingbootcamp.blog.storage.repository.HashtagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostStorage postStorage;
    private HashtagRepository hashRepo;
    private TopicStorage topicStorage;

    public PostController(PostStorage postStorage, HashtagRepository hashRepo, TopicStorage topicStorage) {
        this.postStorage = postStorage;
        this.hashRepo = hashRepo;
        this.topicStorage = topicStorage;
    }

    @GetMapping("/{id}")
    public String displaySinglePost(@PathVariable long id, Model model) {
        model.addAttribute("post", postStorage.retrievePostById(id));
        return "single-post-template";
    }

    @RequestMapping("/addHashtag")
    public String listPostAttachedToHashtag(@RequestParam String hashtag, @RequestParam long id) {
        Post post = postStorage.retrievePostById(id);
        Hashtag hashtagToAdd;
        Optional<Hashtag> hashtagOptional = hashRepo.findByNameIgnoreCase(hashtag);
        if(hashtagOptional.isEmpty()){
            hashtagToAdd = new Hashtag(hashtag);
            hashRepo.save(hashtagToAdd);
        } else{
            hashtagToAdd = hashtagOptional.get();
        }
        post.addHashtag(hashtagToAdd);
        postStorage.save(post);


        return "redirect:/posts/" + id;
    }

    @RequestMapping("/addPost")
    public String addPost(@RequestParam String postTitle, @RequestParam long id, @RequestParam String postAuthor, @RequestParam String postContent) {
       Topic topic = topicStorage.retrieveSingleTopic(id);
       Post post = new Post(postTitle, topic, postContent, postAuthor);
       postStorage.save(post);

        return "redirect:/posts/" + post.getId();


    }
}
