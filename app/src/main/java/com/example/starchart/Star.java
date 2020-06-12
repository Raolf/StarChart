package com.example.starchart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
    //https://en.wikipedia.org/w/api.php?action=query&generator=categorymembers&gcmtitle=Category:Stars_with_proper_names&prop=categories&cllimit=max&gcmlimit=max
public class Star implements Serializable {

    @SerializedName("title")
    @Expose String name;
    float color;

    @SerializedName("pageid")
    @Expose int pageId;

    @SerializedName("")
    @Expose String description;

    public Star(String name, float color, int pageId){
        this.name = name;
        this.color = color;
        this.pageId = pageId;
    }

}
