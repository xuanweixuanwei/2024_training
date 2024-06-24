package com.dejavu.utopia.room.entity;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.dejavu.utopia.converters.DateConverter;
import com.dejavu.utopia.converters.LocalDateConverter;
import com.dejavu.utopia.converters.LocalDateTimeConverter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(tableName = "accounts")
public class Account {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "security_question")
    private String securityQuestion;

    @ColumnInfo(name = "security_answer")
    private String securityAnswer;

    @TypeConverters(LocalDateTimeConverter.class)
    @ColumnInfo(name = "created_time")
    private LocalDateTime createdTime;

    public Account() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public Account(String username, String password, String securityQuestion,
                   String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.createdTime = LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Ignore
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.createdTime = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}