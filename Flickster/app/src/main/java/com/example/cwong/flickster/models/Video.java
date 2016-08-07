package com.example.cwong.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cwong on 8/6/16.
 */
public class Video {
    public String getName() {
        return name;
    }
    public String getSource() {
        return source;
    }
    private String source;
    private String name;

    public Video(JSONObject jsonObject) throws JSONException {
        this.source = jsonObject.getString("source");
        this.name = jsonObject.getString("name");
    }

    public static ArrayList<Video> fromJSONArray(JSONArray array) {
        ArrayList<Video> results = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                results.add(new Video(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}
