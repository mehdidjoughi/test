package com.example.mehdidjo.myapplication2.model;

import android.provider.Settings;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Mehdi Djo on 12/02/2018.
 */

public class Message implements IMessage ,  MessageContentType.Image {

    private String message;
    private String id;
    private Date date;
    private Author author;
    private Image image;



    public Message(String message, String id , Author author) {
        this.message = message;
        this.id = id;
        this.author = author;
        this.date = new Date();
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return message;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    @Override
    public String getImageUrl() {
        return image == null ? null : image.url;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static class Image {

        private String url;

        public Image(String url) {
            this.url = url;
        }
    }
}
