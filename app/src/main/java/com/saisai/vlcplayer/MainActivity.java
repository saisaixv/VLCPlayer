package com.saisai.vlcplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SurfaceView surfaceView;
    private LibVLC libVLC;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.srfc);
        findViewById(R.id.btn_play_sample_1).setOnClickListener(this);
        findViewById(R.id.btn_play_sample_2).setOnClickListener(this);

        ArrayList<String> list=new ArrayList<>();
        libVLC = new LibVLC(getApplication(),list);

        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer(libVLC);
            String url = "udp://@:10000";
//            String url = "/main.ts";

            //
            mediaPlayer.getVLCVout().setVideoSurface(surfaceView.getHolder().getSurface(), surfaceView.getHolder());
            //播放前还要调用这个方法
            mediaPlayer.getVLCVout().attachViews();

            Media media = new Media(libVLC, Uri.parse(url));

            media.setHWDecoderEnabled(true,true);
            mediaPlayer.setMedia(media);
//            mediaPlayer.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (mediaPlayer != null) {
//            mediaPlayer.pause();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mediaPlayer != null) {
//            mediaPlayer.play();
//        }
    }

    @Override
    public void onClick(View view) {
        mediaPlayer.play();
    }
}
