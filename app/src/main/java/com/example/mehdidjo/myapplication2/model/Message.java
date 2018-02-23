package com.example.mehdidjo.myapplication2.model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

/**
 * Created by Mehdi Djo on 12/02/2018.
 */

public class Message implements IMessage ,  MessageContentType.Image {

    private String text;
    private String id;
    private Date date;
    private User user;
    private Image image;



    public Message(String message, String id , User user) {
        this.text = message;
        this.id = id;
        this.user = user;
        this.date = new Date();
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public User getUser() {
        return user;
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
