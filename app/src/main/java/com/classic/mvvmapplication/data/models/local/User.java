package com.classic.mvvmapplication.data.models.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users", indices = {@Index(value = "id", unique = true)})
public class User {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String username;

    @ColumnInfo(name = "session_key")
    private String sessionKey;

    @ColumnInfo(name = "session_key_expire_date")
    private String sessionKeyExpireDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSessionKeyExpireDate() {
        return sessionKeyExpireDate;
    }

    public void setSessionKeyExpireDate(String sessionKeyExpireDate) {
        this.sessionKeyExpireDate = sessionKeyExpireDate;
    }
}
