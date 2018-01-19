
package com.tbg.pixtr.model.pojo.job;

import android.location.Location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbg.pixtr.model.pojo.common.Exif;
import com.tbg.pixtr.model.pojo.common.Links;
import com.tbg.pixtr.model.pojo.common.Urls;
import com.tbg.pixtr.model.pojo.common.User;

import java.util.List;

public class RandomPojo {

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
    @SerializedName("description")
    @Expose
    public Object description;
    @SerializedName("categories")
    @Expose
    public List<Object> categories = null;
    @SerializedName("urls")
    @Expose
    public Urls urls;
    @SerializedName("links")
    @Expose
    public Links links;
    @SerializedName("liked_by_user")
    @Expose
    public boolean likedByUser;
    @SerializedName("sponsored")
    @Expose
    public boolean sponsored;
    @SerializedName("likes")
    @Expose
    public int likes;
    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("current_user_collections")
    @Expose
    public List<Object> currentUserCollections = null;
    @SerializedName("slug")
    @Expose
    public Object slug;
    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("exif")
    @Expose
    public Exif exif;
    @SerializedName("views")
    @Expose
    public int views;
    @SerializedName("downloads")
    @Expose
    public int downloads;

}
