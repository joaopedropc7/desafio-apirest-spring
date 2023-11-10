package br.com.api.capyba.service;

import br.com.api.capyba.exceptions.ResourceNotFoundException;
import br.com.api.capyba.models.PostModel;
import br.com.api.capyba.models.records.PostRecord;
import br.com.api.capyba.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogpotService {

    @Autowired
    private PostRepository postRepository;

    public List<PostModel> getAllPosts(){
        List<PostModel> posts = postRepository.findAll();
        return posts;
    }

    public PostModel findPostById(Integer id){
        PostModel postModel = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        return postModel;
    }

    public PostModel createPost(PostRecord postRecord){
        PostModel post = new PostModel(postRecord);
        postRepository.save(post);
        return post;
    }

    public PostModel updatePost(Integer id, PostRecord postRecord){
        PostModel postDB = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        postDB.setTitle(postRecord.title());
        postDB.setDescription(postRecord.description());
        postDB.setImage(postRecord.image());
        postRepository.save(postDB);
        return postDB;
    }

    public void deletePost(Integer id){
        PostModel postDB = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        postRepository.delete(postDB);
    }

    public void likePost(Integer id){
        PostModel post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        post.setLikes(+1);
        postRepository.save(post);
    }

    public void unlikePost(Integer id){
        PostModel post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        if(post.getLikes() != 0){
            post.setLikes(-1);
            postRepository.save(post);
        }
    }

    public List<PostModel> searchProducts(String query){
        return postRepository.searchPosts(query);
    }
}
