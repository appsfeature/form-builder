package com.formbuilder.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MasterEntity implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;

    public MasterEntity() {

    }
    public MasterEntity(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
