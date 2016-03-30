package com.example.mizuno.prog_signage;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class SignageActivity extends AppCompatActivity {

    VideoView video;
    TextView counter;
    String videoPath;
    int [] videoFileName={R.raw.schedule,R.raw.iwish,R.raw.saka};//ここに動画を追加する。
    int videoPoint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signage);

        video = (VideoView) findViewById(R.id.videoView);
        counter = (TextView) findViewById(R.id.Counter);

        videoPath = "android.resource://" + this.getPackageName() + "/" + videoFileName[videoPoint];
        video.setVideoPath(videoPath);
        Log.d("path", videoPath);
        video.start();

        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                videoPath = "android.resource://" + getPackageName() + "/" + videoFileName[++videoPoint % videoFileName.length];
                video.setVideoPath(videoPath);
                video.seekTo(0);
                video.start();

            }
        });

        //再生時間表示に関する処理
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter.post(new Runnable() {
                    @Override
                    public void run() {
                        counter.setText(String.valueOf((double) video.getCurrentPosition() / 1000) + "s");
                    }
                });
            }
        }, 0, 50);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
            if (video.isPlaying()) video.pause();
            else video.start();
        }
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PLAY) video.start();
        if(keyCode==KeyEvent.KEYCODE_MEDIA_PAUSE) video.pause();
        return super.onKeyDown(keyCode, event);
    }
}
