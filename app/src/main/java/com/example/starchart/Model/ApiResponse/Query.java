package com.example.starchart.Model.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Query implements Serializable {

    @SerializedName("categorymembers")
    @Expose List<Star> starList;

    public List<Star> getStarList() {
        return starList;
    }

    public void setStarList(List<Star> starList) {
        this.starList = starList;
    }


}
