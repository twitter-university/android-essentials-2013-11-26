package com.twitter.android.yamba;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class PostTweetService extends IntentService {
    public final static String ACTION_TWEET_RESULT = "com.twitter.android.yamba.ACTION_TWEET_RESULT";
    public final static String EXTRA_TWEET_RESULT = "EXTRA_TWEET_RESULT";
    public final static String EXTRA_TWEET_MSG = "EXTRA_TWEET_MSG";

    private static final String TAG = "PostTweetService";
    YambaClient mYambaClient;

    public PostTweetService() {
        // Identify our worker thread for debugging purposes
        super(TAG);

        // Create our YambaClient
        mYambaClient = new YambaClient("student", "password");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        boolean result = false;
        String msg = intent.getStringExtra(EXTRA_TWEET_MSG);
        if (!TextUtils.isEmpty(msg)) {
            try {
                mYambaClient.postStatus(msg);
                result = true;
            } catch (YambaClientException e) {
                Log.w(TAG, "Failed to post tweet", e);
            }
        }
        Intent response = new Intent(ACTION_TWEET_RESULT);
        response.putExtra(EXTRA_TWEET_RESULT, result);
        sendBroadcast(response);
    }

    public IBinder onBind(Intent intent) {
        // We don't implement bound service behaviour
        return null;
    }
}
