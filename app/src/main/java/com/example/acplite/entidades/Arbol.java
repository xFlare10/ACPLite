package com.example.acplite.entidades;

import android.graphics.Bitmap;

public class Arbol {
    private int treeID;
    private String treeName;
    private String treeScientificName;
    private Bitmap treeImg;

    public Arbol(int treeID, String treeName, String treeScientificName, Bitmap treeImg) {
        this.treeID = treeID;
        this.treeName = treeName;
        this.treeScientificName = treeScientificName;
        this.treeImg = treeImg;
    }

    public int getTreeID() {
        return treeID;
    }

    public void setTreeID(int treeID) {
        this.treeID = treeID;
    }

    public String getTreeName() {
        return treeName;
    }

    public void setTreeName(String treeName) {
        this.treeName = treeName;
    }

    public String getTreeScientificName() {
        return treeScientificName;
    }

    public void setTreeScientificName(String treeScientificName) {
        this.treeScientificName = treeScientificName;
    }

    public Bitmap getTreeImg() {
        return treeImg;
    }

    public void setTreeImg(Bitmap treeImg) {
        this.treeImg = treeImg;
    }
}
