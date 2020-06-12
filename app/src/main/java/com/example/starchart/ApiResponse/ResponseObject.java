package com.example.starchart.ApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseObject implements Serializable {

    @SerializedName("query")
    @Expose Query query;

    public void setQuery(Query query){
        this.query = query;
    }
    public Query getQuery(){
        return query;
    }
}
