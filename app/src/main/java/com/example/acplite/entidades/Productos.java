package com.example.acplite.entidades;

import android.graphics.Bitmap;

public class Productos {
    private int productID;
    private String productName;
    private float productPrice;
    private Bitmap productImg;

    public Productos(int productID, String productName, float productPrice, Bitmap productImg) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImg = productImg;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public Bitmap getProductImg() {
        return productImg;
    }

    public void setProductImg(Bitmap productImg) {
        this.productImg = productImg;
    }
}
