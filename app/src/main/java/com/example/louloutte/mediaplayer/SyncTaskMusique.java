package com.example.louloutte.mediaplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Created by Louloutte on 28/03/2017.
 */

public class SyncTaskMusique extends AsyncTask<Void, Integer, Void> {

    private Context mContext;
    private SeekBar sbMusic;
    private MediaPlayer mpMusic;

    SyncTaskMusique(Context c, SeekBar sb, MediaPlayer mp) {
        mContext = c;
        sbMusic = sb;
        mpMusic=mp;
    }

    @Override
    protected Void doInBackground(Void... params) {
        while(mpMusic.getCurrentPosition() != mpMusic.getDuration() && !this.isCancelled()) {

            publishProgress(mpMusic.getCurrentPosition());
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mpMusic.start();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        sbMusic.setProgress(values[0]);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        sbMusic.setProgress(0);
        mpMusic.stop();
        mpMusic = null;
    }
}
