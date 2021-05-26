package com.example.acplite.entidades;

import android.graphics.Bitmap;

public class Arbol {
    private String treeName;
    private String treeScientificName;
    private String treeDescription;
    private Bitmap treeImg;

    public Arbol() {
    }

    public Arbol(String treeName, String treeScientificName, String treeDescription, Bitmap treeImg) {
        this.treeName = treeName;
        this.treeScientificName = treeScientificName;
        this.treeDescription = treeDescription;
        this.treeImg = treeImg;
    }

    public Arbol(String treeScientificName, String treeDescription, Bitmap treeImg) {
        this.treeScientificName = treeScientificName;
        this.treeDescription = treeDescription;
        this.treeImg = treeImg;
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

    public String getTreeDescription() {
        return treeDescription;
    }

    public void setTreeDescription(String treeDescription) {
        this.treeDescription = treeDescription;
    }

    public Bitmap getTreeImg() {
        return treeImg;
    }

    public void setTreeImg(Bitmap treeImg) {
        this.treeImg = treeImg;
    }
}
