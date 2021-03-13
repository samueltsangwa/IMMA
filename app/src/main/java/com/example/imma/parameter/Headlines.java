package com.example.imma.parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Headlines {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalRequest")
    @Expose
    private String totalRequest;
    @SerializedName("articles")
    @Expose
    private List <Articles> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(String totalRequest) {
        this.totalRequest = totalRequest;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

}
