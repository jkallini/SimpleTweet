package com.codepath.apps.restclienttemplate.models;

import android.content.Context;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    // twitter date format
    public final String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

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
    /*
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
    } */

    public String getDate() {
        String strDate = "";

        try {
            DateFormat srcDf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);

            // parse the date string into Date object
            Date date = srcDf.parse(createdAt);
            DateFormat destDf = new SimpleDateFormat("h:mm a  •  mm/dd/yy");

            // format the date into another format
            strDate = destDf.format(date);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        return strDate;
    }

    // parse Json string into a relative timestamp
    public String getRelativeTimeAgo() {
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            Date systemDate = sf.parse(createdAt);

            Date userDate = new Date();
            double diff = Math.floor((userDate.getTime() - systemDate.getTime()) / 1000);
            if (diff <= 1) {
                return "just now";
            }
            if (diff < 20) {
                return diff + "s";
            }
            if (diff < 40) {
                return "30s";
            }
            if (diff < 60) {
                return "45s";
            }
            if (diff <= 90) {
                return "1m";
            }
            if (diff <= 3540) {
                return Math.round(diff / 60) + "m";
            }
            if (diff <= 5400) {
                return "1h";
            }
            if (diff <= 86400) {
                return Math.round(diff / 3600) + "h";
            }
            if (diff <= 129600) {
                return "1d";
            }
            if (diff < 604800) {
                return Math.round(diff / 86400) + "d";
            }
            if (diff <= 777600) {
                return "1w";
            }
            return Math.round(diff / 604800) + "w";
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void switchFavorite(Context context, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(context).favoriteTweet(favorited = !favorited, uid, handler);
        favoriteCount += (favorited ? 1 : -1);
    }

    public void switchRetweet(Context context, JsonHttpResponseHandler handler) {
        TwitterApp.getRestClient(context).retweetTweet(retweeted = !retweeted, uid, handler);
        retweetCount += (retweeted ? 1 : -1);
    }


}
