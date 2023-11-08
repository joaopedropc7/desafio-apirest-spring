package br.com.api.capyba.models;

import br.com.api.capyba.models.records.PostRecord;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "posts")
@Table(name = "posts")
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Integer likes;

    public PostModel(PostRecord postRecord) {
        this.title = postRecord.title();
        this.description = postRecord.description();
        this.image = postRecord.image();
        this.likes = 0;
    }

    public PostModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostModel postModel = (PostModel) o;
        return Objects.equals(id, postModel.id) && Objects.equals(title, postModel.title) && Objects.equals(description, postModel.description) && Objects.equals(image, postModel.image) && Objects.equals(likes, postModel.likes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, image, likes);
    }
}
