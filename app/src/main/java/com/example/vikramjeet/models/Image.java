package com.example.vikramjeet.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Vikramjeet on 2/10/15.
 */
public class Image implements Serializable {

    private String Url;
    private String thumbnailUrl;
    private String title;
    private String height;
    private String width;

    public Image(JSONObject json) {
        try {
            Url = json.getString("unescapedUrl");
            thumbnailUrl = json.getString("tbUrl");
            title = json.getString("title");
            height = json.getString("height");
            width = json.getString("width");
        } catch(JSONException e) {
            Log.e("Image", "Failed to parse json in constructor");
            e.printStackTrace();
        }
    }

    public static ArrayList<Image> fromJSONArray(JSONArray array) {
        ArrayList<Image> images = new ArrayList<Image>();
        int length = array.length();
        // Iterate over the imageList json
        for (int index = 0 ; index < length; index++) {
            try {
                // Retrieve each imageJSON object
                JSONObject imageJSON = array.getJSONObject(index);
                // Add it to the images list
                images.add(new Image(imageJSON));
            } catch (JSONException e) {
                Log.e("Image", "Failed to parse json array");
            }
        }
        return images;
    }

    public String getUrl() {
        return Url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

}
