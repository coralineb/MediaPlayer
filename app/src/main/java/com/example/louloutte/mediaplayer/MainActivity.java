package com.example.louloutte.mediaplayer;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mpMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //--------------Barre de progression-------------------
        final SeekBar sbMusic = (SeekBar) findViewById(R.id.sbmusic);
        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mpMusic != null)
                    mpMusic.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //----------bouton play---------------------
        Button start=(Button)findViewById(R.id.play) ;
        final SyncTaskMusique[] mt = new SyncTaskMusique[1];
        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mpMusic == null && mt[0] == null)
                {
                    mpMusic = MediaPlayer.create(getApplicationContext(), R.raw.mario);
                    mpMusic.setLooping(false);
                    sbMusic.setMax(mpMusic.getDuration());
                    mt[0] = new SyncTaskMusique(getApplicationContext(), sbMusic, mpMusic);
                    mt[0].execute();
                }
            }
        });

        Button stop=(Button)findViewById(R.id.stop) ;
        stop.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mt[0] != null) {
                    mt[0].cancel(true);
                    mt[0] = null;
                    mpMusic = null;
                }
            }
        });


        //-----------------Gestion du son------------------
        // audioManager gère le son du player
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        SeekBar sbson = (SeekBar) findViewById(R.id.sbson);
        // Par défaut on met le son au milieu
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC/2), AudioManager.FLAG_SHOW_UI);
        // Le maximum de ma barre corrrespond au volume du son maximum
        sbson.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        sbson.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_SHOW_UI);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}

