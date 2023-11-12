package br.com.api.capyba.controller;

import br.com.api.capyba.models.PostModel;
import br.com.api.capyba.models.records.PostRecord;
import br.com.api.capyba.service.BlogpotService;
import br.com.api.capyba.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private BlogpotService postBlogService;
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Finds all posts", description = "Finds all posts",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PostModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public Page<PostModel> findAll(@RequestParam(value = "page", defaultValue = "0")Integer page,
                                   @RequestParam(value = "size", defaultValue = "10")Integer size,
                                   HttpServletRequest request){
        userService.getIdUserByAuthentication(request);
        return postBlogService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Finds post by id", description = "Finds post",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PostModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public PostModel getPostByiD(@PathVariable(value = "id")Integer id){
        return postBlogService.findPostById(id);
    }

    @PostMapping
    @Operation(summary = "Adds a new Post",
            description = "Adds a new Post by passing in a JSON representation of the post!",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PostModel.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public PostModel createPost(@RequestBody PostRecord postRecord){
        return postBlogService.createPost(postRecord);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Post",
            description = "Updates a Post by passing in a JSON representation of the Post!",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "Updated", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PostModel.class))
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public PostModel updatePost(@PathVariable(value = "id") Integer id, @RequestBody PostRecord postRecord){
        return postBlogService.updatePost(id, postRecord);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Post",
            description = "Deletes a Post by passing ID in a URL!",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public void deletePost(@PathVariable(value = "id")Integer id){
        postBlogService.deletePost(id);
    }

    @PostMapping("/like/{id}")
    @Operation(summary = "Like a Post",
            description = "Like a Post by passing ID in a URL!",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public void likePost(@PathVariable(value = "id")Integer id){
        postBlogService.likePost(id);
    }

    @PostMapping("/unlike/{id}")
    @Operation(summary = "Unlike a Post",
            description = "Unlike a Post by passing ID in a URL!",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public void unlikePost(@PathVariable(value = "id")Integer id){
        postBlogService.unlikePost(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Finds all posts by search", description = "Finds all posts by search, using /search?query=...",
            tags = {"Post"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PostModel.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<PostModel>> searchPosts(@RequestParam("query")String query){
        return ResponseEntity.ok(postBlogService.searchProducts(query));
    }

}
