
package com.tbg.pixtr.model.pojo.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Exif {

    @SerializedName("make")
    @Expose
    public String make;
    @SerializedName("model")
    @Expose
    public String model;
    @SerializedName("exposure_time")
    @Expose
    public String exposureTime;
    @SerializedName("aperture")
    @Expose
    public String aperture;
    @SerializedName("focal_length")
    @Expose
    public String focalLength;
    @SerializedName("iso")
    @Expose
    public Integer iso;

}
