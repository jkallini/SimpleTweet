package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Entities {

    // attributes
    public String mediaUrl;
    public boolean hasEntity;

    // generic constructor
    public Entities() {
    }

    // deserialize the JSON
    public static Entities fromJSON(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();

        try {
            JSONArray media = jsonObject.getJSONArray("media");
            JSONObject object = media.getJSONObject(0);
            entities.mediaUrl = object.getString("media_url_https");
            entities.hasEntity=true;
        } catch (JSONException e) {
            entities.mediaUrl = "";
            entities.hasEntity=false;
        }
        return entities;
    }
}
