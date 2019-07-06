package com.codepath.apps.restclienttemplate;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/scribejava/scribejava/tree/master/scribejava-apis/src/main/java/com/github/scribejava/apis
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {

	public static final BaseApi REST_API_INSTANCE = TwitterApi.instance(); // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	//public static final String REST_CONSUMER_KEY = "4KxocRp2Wh8RZ9cy1KJEjxGVy";       // Change this
	//public static final String REST_CONSUMER_SECRET = "EeyJ4vEZN3al7c0C13bMwAY3pGc2RASrampYtvJvnX1kLDHKJf"; // Change this
	//public static final String REST_CONSUMER_KEY = "ABaCtvkJthocDnKfbw8REoOW4";
	//public static final String REST_CONSUMER_SECRET = "uVntI345xJ0vTSyExmbTqUvLd2vo8SVznfiLo3tewrsjUodeTa";
	public static final String REST_CONSUMER_KEY = "rllBCzpDlSKxcP4KLfYaHIqtV";
	public static final String REST_CONSUMER_SECRET = "kybWvvX9paFvOiUjNG7S8Mgexi2XVMp37eisBRfUfUtkXFpT3z";

	// Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
	public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

	// See https://developer.chrome.com/multidevice/android/intents
	public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

	public TwitterClient(Context context) {
		super(context, REST_API_INSTANCE,
				REST_URL,
				REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET,
				String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
						context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", 25);
		if (maxId != 0) params.put("max_id", maxId - 1);
		client.get(apiUrl, params, handler);
	}

	// define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	public void sendTweet(String message, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", message);
		client.post(apiUrl, params, handler);
	}

	// reply function, similar to sendTweet
	public void replyTweet(String reply, long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("status", reply);
		params.put("in_reply_to_status_id", uid);
		client.post(apiUrl, params, handler);
	}

	public void favoriteTweet(boolean favorited, long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/" + (favorited ? "create" : "destroy") + ".json");
		RequestParams params = new RequestParams();
		params.put("id", uid);
		client.post(apiUrl, params, handler);
	}

	public void retweetTweet(boolean retweeted, long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/" + (retweeted ? "retweet" : "unretweet")
				                  + "/" + uid + ".json");
		RequestParams params = new RequestParams();
		params.put("id", uid);
		client.post(apiUrl, params, handler);
	}
}
