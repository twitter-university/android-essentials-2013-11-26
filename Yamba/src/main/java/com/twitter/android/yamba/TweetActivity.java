package com.twitter.android.yamba;

import android.app.Activity;
import android.os.Bundle;

public class TweetActivity extends Activity
        implements TweetFragment.OnTweetSentListener {

    private static final String TAG = "TweetActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tweet);
        TweetFragment tweetFragment
                = (TweetFragment) getFragmentManager().findFragmentById(R.id.tweet_fragment);
        tweetFragment.setOnTweetSentListener(this);
    }

    @Override
    public void onTweetSent() {
        finish();
    }
}
