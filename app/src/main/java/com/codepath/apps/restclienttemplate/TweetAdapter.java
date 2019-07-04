package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.TweetDetailsActivity;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets; // list of tweets
    Context context;

    // constructor takes a tweet array
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;

    }

    // for each row, inflate the layout and cache references into ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // get the data according to the position
        Tweet tweet = mTweets.get(position);

        // populate the views according to this data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvCreatedAt.setText(" • " + getRelativeTimeAgo(tweet.createdAt));
        holder.tvScreenName.setText("@" + tweet.user.screenName);

        // populate the profile image using glide
        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
        if (tweet.entities.hasEntity) {
            String mediaUrl= tweet.entities.mediaUrl;
            //Glide.with(context).load(mediaUrl).into(holder.ivMedia);
            Glide.with(context)
                    .load(tweet.entities.mediaUrl)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                    .into(holder.ivMedia);
            holder.ivMedia.setVisibility(View.VISIBLE);
        } else {
            holder.ivMedia.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // custom Viewholder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // references of views from the itemView passed in constructor
        public ImageView ivProfileImage; // profile image
        public TextView tvUsername;      // user's name, appears in bold
        public TextView tvBody;          // tweet text
        public TextView tvCreatedAt;     // timestamp
        public TextView tvScreenName;    // user's @ name
        public ImageView ivMedia;        // embedded media
        public ImageView ivReply;        // reply button

        public ViewHolder(View itemView) {
            super(itemView);

            // lookup views
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);
            ivReply = (ImageView) itemView.findViewById(R.id.ivReply);

            itemView.setOnClickListener(this);
            ivReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // compose a tweet when reply button is clicked
                    if (context instanceof TimelineActivity) {
                        String reply = tvScreenName.getText().toString();
                        ((TimelineActivity) context).composeTweet(reply);
                    }
                    else {
                        Log.d("TweetAdapter", "context is not TimelineActivity");
                    }
                }
            });
        }

        public void onClick(View v) {

            // get item's position
            int position = getAdapterPosition();

            // check that the position exists
            if (position != RecyclerView.NO_POSITION) {
                // get the tweet and send it's intent to the new activity
                Tweet tweet = mTweets.get(position);
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                context.startActivity(intent);
            }
        }
    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
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

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
}
