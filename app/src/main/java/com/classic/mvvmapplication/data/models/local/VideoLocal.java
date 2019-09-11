package com.classic.mvvmapplication.data.models.local;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "videos", indices = {@Index(value = "id", unique = true)})
public class VideoLocal {


    @PrimaryKey
    private String id;

    private int rel_id;

    private String rel_type;

    private String iso6391;

    private String iso31661;

    private String key;

    private String name;

    private String site;

    private Integer size;

    private String type;

    public VideoLocal(String id, int rel_id, String rel_type, String iso6391, String iso31661, String key, String name, String site, Integer size, String type) {
        this.id = id;
        this.rel_id = rel_id;
        this.rel_type = rel_type;
        this.iso6391 = iso6391;
        this.iso31661 = iso31661;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIso6391() {
        return iso6391;
    }

    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    public String getIso31661() {
        return iso31661;
    }

    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



}
