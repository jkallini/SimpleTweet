package com.codepath.apps.restclienttemplate.models;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 0; // request code
    ImageButton ibTweetButton;                // "Tweet" button
    EditText etTweet;                         // textbox that hold's user input
    TextView tvCharsLeft;                     // number of characters left
    TwitterClient client;                     // twitter client
    final int CHAR_TOTAL = 280;               // total chars
    Tweet toReply;                            // tweet that may be replied to


    // watcher keeps track of characters remaining, updates text view
    private final TextWatcher watcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            tvCharsLeft.setText(String.valueOf(CHAR_TOTAL - s.length()));
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        // get references to user's text and tweet button
        ibTweetButton = findViewById(R.id.ibTweetButton);
        etTweet = findViewById(R.id.etTweet);
        tvCharsLeft = (TextView) findViewById(R.id.tvCharsLeft);

        // set default 280 characters
        tvCharsLeft.setText(Integer.toString(CHAR_TOTAL));
        etTweet.addTextChangedListener(watcher);

        // check if the tweet to be composed is a reply
        String reply = getIntent().getStringExtra("reply");
        etTweet.setText(reply);

        ibTweetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String tweetText = etTweet.getText().toString();

                // send the user's tweet
                client.sendTweet(tweetText, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            // get tweet from JSON response
                            Tweet tweet = Tweet.fromJSON(response);

                            // create intent and set result
                            Intent intent = new Intent();
                            intent.putExtra("new_tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, intent);
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
