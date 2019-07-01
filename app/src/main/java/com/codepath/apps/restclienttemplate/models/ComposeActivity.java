package com.codepath.apps.restclienttemplate.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0; // request code
    ImageButton ibTweetButton;
    EditText etTweet;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        // get references to user's text and tweet button
        ibTweetButton = findViewById(R.id.ibTweetButton);
        etTweet = findViewById(R.id.etTweet);

        ibTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String tweetText = etTweet.getText().toString();

                client.sendTweet(tweetText, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //
                            Tweet tweet = Tweet.fromJSON(response);

                            Intent intent = new Intent();
                            intent.putExtra("new_tweet", tweet);
                            setResult(0, intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
