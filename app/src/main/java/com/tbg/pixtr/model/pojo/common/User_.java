
package com.tbg.pixtr.model.pojo.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User_ {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("portfolio_url")
    @Expose
    public String portfolioUrl;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("followed_by_user")
    @Expose
    public boolean followedByUser;
    @SerializedName("total_likes")
    @Expose
    public int totalLikes;
    @SerializedName("total_photos")
    @Expose
    public int totalPhotos;
    @SerializedName("total_collections")
    @Expose
    public int totalCollections;
    @SerializedName("profile_image")
    @Expose
    public ProfileImage_ profileImage;
    @SerializedName("links")
    @Expose
    public Links__ links;

}
