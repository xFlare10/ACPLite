package com.example.acplite.entidades;

public class Publicacion {
    private int postID;
    private String postName;
    private String postDescription;

    public Publicacion(int postID, String postName, String postDescription) {
        this.postID = postID;
        this.postName = postName;
        this.postDescription = postDescription;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }
}
