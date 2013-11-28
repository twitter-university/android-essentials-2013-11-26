package com.twitter.android.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class TweetResponseReceiver extends BroadcastReceiver {
    private static final String TAG = "TweetResponseReceiver";

    public void onReceive(Context context, Intent intent) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Received post acknowledgement");
        boolean result = intent.getBooleanExtra(PostTweetService.EXTRA_TWEET_RESULT, false);
        Toast.makeText(
              context,
              result ? R.string.post_status_succeed : R.string.post_status_fail,
              Toast.LENGTH_LONG
        ).show();
    }
}
