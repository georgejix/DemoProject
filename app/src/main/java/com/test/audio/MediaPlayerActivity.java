package com.test.audio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.App;
import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@ContentView(R.layout.activity_media_player)
public class MediaPlayerActivity extends BaseActivity {
    private static final String TAG = "MediaPlayerActivity";
    private MediaPlayer mMediaPlayer;
    private SoundPool soundPool;
    private String audioPath1, audioPath2;

    private LinkedBlockingQueue<String> mMediaPlayerQueue;
    private PlayAudioThread mPlayAudioThread;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioPath1 = App.getFile() + File.separator + "fcw_h_be.wav";
        audioPath2 = App.getFile() + File.separator + "fcw_h_be.wav";
        if(null == soundPool)
            soundPool = new SoundPool(2, AudioManager.STREAM_ALARM, 0);
        soundId = soundPool.load(audioPath2, 1);
        mPlayAudioThread = new PlayAudioThread();
        mPlayAudioThread.start();
    }

    public void play1(View view){
        /*if(null != mediaPlayer && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(audioPath1)));
        mediaPlayer.start();*/
        if(null == mMediaPlayerQueue)
            return;
        try {
            mMediaPlayerQueue.offer(audioPath1, 10 * 1000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int soundId;
    int streamid = -1;
    public void play2(View view){
        if(-1 == streamid) {
            streamid = soundPool.play(soundId, 1, 1, 1, -1, 1);
        }else{
            soundPool.stop(streamid);
            streamid = -1;
        }
    }

    class PlayAudioThread extends Thread{
        @Override
        public void run() {
            if(null == mMediaPlayerQueue){
                mMediaPlayerQueue = new LinkedBlockingQueue<String>();
            }
            String path;
            while(!isInterrupted()){
                try {
                    path = mMediaPlayerQueue.take();
                    if(null == path)
                        continue;
                    while(null != mMediaPlayer && mMediaPlayer.isPlaying()){
                        SystemClock.sleep(200);
                    }
                    if(null != mMediaPlayer){
                        mMediaPlayer.release();
                        Log.d(TAG, "release");
                    }
                    mMediaPlayer = MediaPlayer.create(MediaPlayerActivity.this,
                            Uri.fromFile(new File(path)));
                    mMediaPlayer.start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
