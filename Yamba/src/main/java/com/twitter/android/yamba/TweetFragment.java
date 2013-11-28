package com.twitter.android.yamba;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.lang.ref.WeakReference;

public class TweetFragment extends Fragment
        implements View.OnClickListener {

    private static final String TAG = "TweetFragment";

    EditText mEditTweet;
    WeakReference<OnTweetSentListener> mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View top = inflater.inflate(R.layout.fragment_tweet, container, false);
        mEditTweet = (EditText) top.findViewById(R.id.edit_tweet);
        Button buttonTweet = (Button) top.findViewById(R.id.button_tweet);
        buttonTweet.setOnClickListener(this);
        return top;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_tweet:
                // Tweet button was clicked
                String msg = mEditTweet.getText().toString();
                mEditTweet.setText("");
                if (!TextUtils.isEmpty(msg)) {
                    Log.d(TAG, "User entered: " + msg);
                    Intent intent = new Intent(getActivity(), PostTweetService.class);
                    intent.putExtra(PostTweetService.EXTRA_TWEET_MSG, msg);
                    getActivity().startService(intent);
                }
                notifyOnTweetSentListener();
                break;
            default:
                // We should never get here! Unknown button clicked?
        }
    }

    private void notifyOnTweetSentListener() {
        OnTweetSentListener listener = (mListener == null) ? null : mListener.get();
        if (listener != null) {
            listener.onTweetSent();
        }
    }

    public void setOnTweetSentListener(OnTweetSentListener listener) {
        mListener = new WeakReference<OnTweetSentListener>(listener);
    }

    public interface OnTweetSentListener {
        void onTweetSent();
    }
}