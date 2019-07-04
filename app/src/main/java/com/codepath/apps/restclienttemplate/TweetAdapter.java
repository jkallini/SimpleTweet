package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import com.loopj.android.http.JsonHttpResponseHandler;

import org.parceler.Parcels;

import java.util.List;

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
        final ViewHolder viewHolder = new ViewHolder(tweetView);

        viewHolder.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Tweet tweet = mTweets.get(position);
                tweet.switchFavorite(context, new JsonHttpResponseHandler());
                setButton(viewHolder.ivHeart, tweet.favorited,
                        R.drawable.ic_vector_heart_stroke, R.drawable.ic_vector_heart, R.color.medium_red);
            }
        });

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
        holder.tvCreatedAt.setText(" • " + tweet.getRelativeTimeAgo());
        holder.tvScreenName.setText("@" + tweet.user.screenName);

        // set color of favorite button
        setButton(holder.ivHeart, tweet.favorited, R.drawable.ic_vector_heart_stroke, R.drawable.ic_vector_heart, R.color.medium_red);

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

    // sets the color of a button, depending on whether it is active
    private void setButton(ImageView iv, boolean isActive, int strokeResId, int fillResId, int activeColor) {
        iv.setImageResource(isActive ? fillResId : strokeResId);
        iv.setColorFilter(ContextCompat.getColor(context, isActive ? activeColor : R.color.medium_gray));
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
        public ImageView ivHeart;        // like button

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
            ivHeart = (ImageView) itemView.findViewById(R.id.ivHeart);

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
