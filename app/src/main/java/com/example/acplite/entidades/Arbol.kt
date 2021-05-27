package com.example.acplite.entidades

import android.graphics.Bitmap

class Arbol {
    var treeName: String? = null
    var treeScientificName: String? = null
    var treeDescription: String? = null
    var treeImg: Bitmap? = null

    constructor() {}
    constructor(
        treeName: String?,
        treeScientificName: String?,
        treeDescription: String?,
        treeImg: Bitmap?
    ) {
        this.treeName = treeName
        this.treeScientificName = treeScientificName
        this.treeDescription = treeDescription
        this.treeImg = treeImg
    }

    constructor(treeScientificName: String?, treeDescription: String?, treeImg: Bitmap?) {
        this.treeScientificName = treeScientificName
        this.treeDescription = treeDescription
        this.treeImg = treeImg
    }
}