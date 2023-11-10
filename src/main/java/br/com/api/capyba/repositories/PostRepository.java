package br.com.api.capyba.repositories;

import br.com.api.capyba.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Integer> {

    @Query("SELECT p FROM posts p WHERE " +
            "p.title ILIKE CONCAT('%', :query, '%') " +
            "OR p.description ILIKE CONCAT('%', :query, '%')")
    List<PostModel> searchPosts(String query);

}
