package com.example.vikramjeet.models;

import java.io.Serializable;

/**
 * Created by Vikramjeet on 2/12/15.
 */
public class Filter implements Serializable {
    private String imageSize;
    private String imageColor;
    private String imageType;
    private String siteFilter;

    public Filter(String size, String color, String type, String site) {
        imageSize = size;
        imageColor = color;
        imageType = type;
        siteFilter = site;

    }

    public String getImageSize() {
        return imageSize;
    }

    public String getImageColor() {
        return imageColor;
    }

    public String getImageType() {
        return imageType;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

}
