package com.codepath.apps.restclienttemplate.models;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetDetailsActivity extends AppCompatActivity {

    private Tweet tweet;
    Context context;
    /*
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvScreenName) TextView tvScreenName;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvTime) TextView tvTime;
     */

    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvScreenName) TextView tvScreenName;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
    @BindView(R.id.ivMedia) ImageView ivMedia;

    @BindView(R.id.ivFavorite) ImageView ivFavorite;
    @BindView(R.id.tvFavoriteCount) TextView tvFavoriteCount;
    @BindView(R.id.ivRetweet) ImageView ivRetweet;
    @BindView(R.id.tvRetweetCount) TextView tvRetweetCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME |
                ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_USE_LOGO);
        actionBar.setIcon(R.drawable.twitter_logo);

        context = getParent();

        ButterKnife.bind(this);

        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivMedia = (ImageView) findViewById(R.id.ivMedia);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvUsername.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.getDate());

        // set color of favorite button
        setButton(ivFavorite, tweet.favorited, R.drawable.ic_vector_heart_stroke, R.drawable.ic_vector_heart, R.color.medium_red);
        setButton(ivRetweet, tweet.retweeted, R.drawable.ic_vector_retweet_stroke, R.drawable.ic_vector_retweet, R.color.medium_green);
        tvFavoriteCount.setText(String.format("%d Likes", tweet.favoriteCount));
        tvRetweetCount.setText(String.format("%d Retweets", tweet.retweetCount));


        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);

        if (tweet.entities.hasEntity) {
            Glide.with(this)
                    .load(tweet.entities.mediaUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                    .into(ivMedia);
            ivMedia.setVisibility(View.VISIBLE);
        } else {
            ivMedia.setVisibility(View.GONE);
        }

        // enable favorite button
        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet.switchFavorite(context, new JsonHttpResponseHandler());
                setButton(ivFavorite, tweet.favorited,
                        R.drawable.ic_vector_heart_stroke, R.drawable.ic_vector_heart, R.color.medium_red);
                tvFavoriteCount.setText(String.format("%d Likes", tweet.favoriteCount));
            }
        });

        // enable retweet button
        ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tweet.switchRetweet(context, new JsonHttpResponseHandler());
                setButton(ivRetweet, tweet.retweeted,
                        R.drawable.ic_vector_retweet_stroke, R.drawable.ic_vector_retweet, R.color.medium_green);
                tvRetweetCount.setText(String.format("%d Retweets", tweet.retweetCount));
            }
        });
    }

    // sets the color of a button, depending on whether it is active
    private void setButton(ImageView iv, boolean isActive, int strokeResId, int fillResId, int activeColor) {
        iv.setImageResource(isActive ? fillResId : strokeResId);
        iv.setColorFilter(ContextCompat.getColor(this, isActive ? activeColor : R.color.medium_gray));
    }
}
