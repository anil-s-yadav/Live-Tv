package com.legendarysoftwares.livetv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.legendarysoftwares.livetv.databinding.ActivityVideoplayerBinding;

public class videoplayer extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    String videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);

        playerView=findViewById(R.id.playerView);

        Intent intent = getIntent();
        videoUri = intent.getStringExtra("Link") ;

        try {
            exoPlayer = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(exoPlayer);
            MediaItem mediaItem = MediaItem.fromUri(videoUri);
            exoPlayer.setMediaItem(mediaItem);
            exoPlayer.prepare();
            exoPlayer.play();
        }catch (Exception e){
            throw new RuntimeException(e);
            //Log.d("Why Crashed", "onCreate.videoplayer.java "+e);
        }

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        exoPlayer.stop();
        exoPlayer.prepare();
        exoPlayer.play();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        exoPlayer.prepare();
        exoPlayer.play();
    }
    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.stop();
    }

}