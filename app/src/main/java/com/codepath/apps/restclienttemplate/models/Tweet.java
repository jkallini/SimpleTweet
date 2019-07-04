package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.text.format.DateUtils;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Parcel
public class Tweet {

    // list out the attributes
    public String body;
    public long uid;         // database ID for the tweet
    public String createdAt;
    public User user;
    public Entities entities;

    // fields for favoriting a tweet
    public long favoriteCount;
    public boolean favorited;

    // fields for retweeting
    public long retweetCount;
    public boolean retweeted;

    // generic constructor
    public Tweet() {

    }

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.entities = Entities.fromJSON(jsonObject.getJSONObject("entities"));
        tweet.favoriteCount = jsonObject.getLong("favorite_count");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.retweetCount = jsonObject.getLong("retweet_count");
        tweet.retweeted = jsonObject.getBoolean("retweeted");

        return tweet;
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo() {
        String rawJsonDate = createdAt;
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public void switchFavorite(Context context, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(context).favoriteTweet(favorited = !favorited, uid, handler);
        favoriteCount = (favorited ? 1 : -1);
    }

    public void switchRetweet(Context context, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(context).retweetTweet(retweeted = !retweeted, uid, handler);
        retweetCount = (retweeted ? 1 : -1);
    }
}
