
package com.tbg.pixtr.model.pojo.collections;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tbg.pixtr.model.pojo.common.CoverPhoto;
import com.tbg.pixtr.model.pojo.common.Links___;
import com.tbg.pixtr.model.pojo.common.User_;

public class CollectionsPojo {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("published_at")
    @Expose
    public String publishedAt;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("curated")
    @Expose
    public boolean curated;
    @SerializedName("featured")
    @Expose
    public boolean featured;
    @SerializedName("total_photos")
    @Expose
    public int totalPhotos;
    @SerializedName("private")
    @Expose
    public boolean _private;
    @SerializedName("share_key")
    @Expose
    public String shareKey;
    @SerializedName("cover_photo")
    @Expose
    public CoverPhoto coverPhoto;
    @SerializedName("user")
    @Expose
    public User_ user;
    @SerializedName("links")
    @Expose
    public Links___ links;

    public int type = 1;

}
