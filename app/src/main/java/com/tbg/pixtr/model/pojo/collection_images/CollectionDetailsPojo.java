
package com.tbg.pixtr.model.pojo.collection_images;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbg.pixtr.model.pojo.common.Category;
import com.tbg.pixtr.model.pojo.common.Links__;
import com.tbg.pixtr.model.pojo.common.Urls;
import com.tbg.pixtr.model.pojo.common.User;

import java.util.ArrayList;
import java.util.List;

public class CollectionDetailsPojo {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("width")
    @Expose
    public int width;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("color")
    @Expose
    public String color;
    @SerializedName("likes")
    @Expose
    public int likes;
    @SerializedName("liked_by_user")
    @Expose
    public boolean likedByUser;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("current_user_collections")
    @Expose
    public List<Object> currentUserCollections = new ArrayList<>();
    @SerializedName("urls")
    @Expose
    public Urls urls;
    @SerializedName("categories")
    @Expose
    public List<Category> categories = new ArrayList<>();
    @SerializedName("links")
    @Expose
    public Links__ links;

    @Override
    public boolean equals(Object obj) {
        if(obj!= null && obj instanceof CollectionDetailsPojo){
            return id.equalsIgnoreCase(((CollectionDetailsPojo) obj).id);
        }
        return false;
    }
}
