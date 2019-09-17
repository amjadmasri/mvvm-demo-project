package com.classic.mvvmapplication.data.models.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "review",indices = {@Index(value = "id", unique = true)})
public class ReviewLocal {

    public ReviewLocal(@NonNull String id, String author, String content, String url, int rel_id, String rel_type) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
        this.rel_id = rel_id;
        this.rel_type = rel_type;
    }

    @NonNull
    @PrimaryKey
    private String id;

    private String author;

    private String content;

    private String url;

    private int rel_id;

    private String rel_type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRel_id() {
        return rel_id;
    }

    public void setRel_id(int rel_id) {
        this.rel_id = rel_id;
    }

    public String getRel_type() {
        return rel_type;
    }

    public void setRel_type(String rel_type) {
        this.rel_type = rel_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewLocal that = (ReviewLocal) o;
        return getRel_id() == that.getRel_id() &&
                getId().equals(that.getId()) &&
                Objects.equals(getAuthor(), that.getAuthor()) &&
                Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getUrl(), that.getUrl()) &&
                Objects.equals(getRel_type(), that.getRel_type());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getContent(), getUrl(), getRel_id(), getRel_type());
    }
}
