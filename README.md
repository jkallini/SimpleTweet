# Project 3 - *SimpleTweet*

**SimpleTweet** is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: **20** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x]	User can **sign in to Twitter** using OAuth login
* [x]	User can **view tweets from their home timeline**
  * [x] User is displayed the username, name, and body for each tweet
  * [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
* [x] User can **compose and post a new tweet**
  * [x] User can click a “Compose” icon in the Action Bar on the top right
  * [x] User can then enter a new tweet and post this to twitter
  * [x] User is taken back to home timeline with **new tweet visible** in timeline
  * [x] Newly created tweet should be manually inserted into the timeline and not rely on a full refresh

The following **stretch** features are implemented:

* [x] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [x] User can **pull down to refresh tweets timeline**
* [x] User is using **"Twitter branded" colors and styles**
* [x] User sees an **indeterminate progress indicator** when any background or network task is happening
* [x] User can **select "reply" from detail view to respond to a tweet**
  * [x] User that wrote the original tweet is **automatically "@" replied in compose**
* [x] User can tap a tweet to **open a detailed tweet view**
  * [x] User can **take favorite (and unfavorite) or retweet** actions on a tweet
* [x] User can **see embedded image media within a tweet** on list or detail view.

The following **bonus** features are implemented:

* [x] User can view more tweets as they scroll with infinite pagination
* [x] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [x] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.org/android/Using-Parceler).
* [x] Replace all icon drawables and other static image assets with [vector drawables](http://guides.codepath.org/android/Drawables#vector-drawables) where appropriate.
* [x] Use the popular ButterKnife annotation library to reduce view boilerplate.


The following **additional** features are implemented:

* [x] The **number of retweets and favorites** appears on each tweet.
  * [x] The count increments when "retweet" or "favorite" is clicked.
* [x] The date of a tweet (in Twitter's format) appears on the detailed view.
* [x] The detailed view is scrollable (allowing larger images to be viewed).
* [x] The home screen has a BottomNavigationView with home, search, and notifications buttons.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/jkallini/MySimpleTweets/blob/master/walkthrough_timeline.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />
<img src='https://github.com/jkallini/MySimpleTweets/blob/master/walkthrough_progressbar.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android

## License

    Copyright 1999 Julie Kallini

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.