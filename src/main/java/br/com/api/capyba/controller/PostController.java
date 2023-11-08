package br.com.api.capyba.controller;

import br.com.api.capyba.models.PostModel;
import br.com.api.capyba.models.records.PostRecord;
import br.com.api.capyba.service.BlogpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private BlogpotService postBlogService;

    @GetMapping
    public List<PostModel> getAllPosts(){
        return postBlogService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostModel getPostByiD(@PathVariable(value = "id")Integer id){
        return postBlogService.findPostById(id);
    }

    @PostMapping
    public PostModel createPost(@RequestBody PostRecord postRecord){
        return postBlogService.createPost(postRecord);
    }

    @PutMapping("/{id}")
    public PostModel updatePost(@PathVariable(value = "id") Integer id, @RequestBody PostRecord postRecord){
        return postBlogService.updatePost(id, postRecord);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable(value = "id")Integer id){
        postBlogService.deletePost(id);
    }

    @PostMapping("/like/{id}")
    public void likePost(@PathVariable(value = "id")Integer id){
        postBlogService.likePost(id);
    }

    @PostMapping("/unlike/{id}")
    public void unlikePost(@PathVariable(value = "id")Integer id){
        postBlogService.unlikePost(id);
    }


}
