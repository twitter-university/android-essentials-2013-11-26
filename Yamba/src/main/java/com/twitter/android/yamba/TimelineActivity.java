package com.twitter.android.yamba;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.twitter.university.android.yamba.service.YambaContract;

public class TimelineActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TIMELINE_LOADER = 42;

    private static final String[] FROM = {
        YambaContract.Timeline.Columns.HANDLE,
        YambaContract.Timeline.Columns.TWEET,
        YambaContract.Timeline.Columns.TIMESTAMP,
    };

    private static final int[] TO = {
        R.id.text_status_user,
        R.id.text_status_msg,
        R.id.text_status_time,
    };

    SimpleCursorAdapter mAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new SimpleCursorAdapter(
                this,
                R.layout.timeline_row,
                null,
                FROM,
                TO,
                0);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int id = view.getId();
                switch (id) {
                    case R.id.text_status_time:
                        // Customize the date format
                        long timestamp = cursor.getLong(columnIndex);
                        CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
                        ((TextView) view).setText(relTime);
                        return true;
                    default:
                        // Let the SimpleCursorAdapter do the default binding
                        return false;
                }
            }
        });

        setListAdapter(mAdapter);

        getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                this,
                YambaContract.Timeline.URI,
                null,
                null,
                null,
                YambaContract.Timeline.Columns.TIMESTAMP + " DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_tweet) {
            // Display the TweetActivity
            startActivity( new Intent(this, TweetActivity.class) );
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}