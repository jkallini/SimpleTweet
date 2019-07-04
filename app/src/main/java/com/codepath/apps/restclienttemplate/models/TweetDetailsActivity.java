package com.codepath.apps.restclienttemplate.models;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.R;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    private Tweet tweet;
    /*
    @BindView(R.id.tvUsername) TextView tvUsername;
    @BindView(R.id.tvScreenName) TextView tvScreenName;
    @BindView(R.id.tvBody) TextView tvBody;
    @BindView(R.id.tvTime) TextView tvTime;
     */

    TextView tvUsername;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvTime;
    ImageView ivProfileImage;
    ImageView ivMedia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        // ButterKnife.bind(this);

        tvUsername = (TextView) findViewById(R.id.tvUsername);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTime = (TextView) findViewById(R.id.tvTime);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        ivMedia = (ImageView) findViewById(R.id.ivMedia);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        tvUsername.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTime.setText(tweet.createdAt);

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
    }
}
