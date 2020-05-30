package com.videoview;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

import java.io.IOException;

@ContentView(R.layout.activity_surface_view)
public class SurfaceViewActivity extends BaseActivity implements SurfaceHolder.Callback
{
    private final static String TAG = "VideoViewActivity";

    @ViewInject(R.id.surfaceView)
    SurfaceView mSurfaceView;

    private String video1 = "https://img.1000.com/qm-a-img/prod/2775370/967d971876916b079401f6761d1de54f.mp4";

    private String video2 = "https://img.1000.com/qm-a-img/prod/2775370/89fddf6be17ffde4b77f308bbe5eaf11.mp4";

    private String videoUrl[] = new String[2];

    private int playIndex = 0;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        playVideo();
    }

    private void playVideo()
    {
        videoUrl[0] = video1;
        videoUrl[1] = video2;
        mMediaPlayer = new MediaPlayer();
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        mMediaPlayer.setDisplay(holder);
        mMediaPlayer.setOnPreparedListener(mp -> {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSurfaceView.getLayoutParams();
            params.width = mMediaPlayer.getVideoWidth();
            params.height = mMediaPlayer.getVideoHeight();
            mSurfaceView.setLayoutParams(params);
            Log.d(TAG, mMediaPlayer.getVideoWidth() + "," + mMediaPlayer.getVideoHeight());
            mMediaPlayer.start();
        });
        mMediaPlayer.setOnCompletionListener(mp -> {
            mMediaPlayer.reset();
            try
            {
                mMediaPlayer.setDataSource(videoUrl[playIndex++ % 2]);
                mMediaPlayer.prepareAsync();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        try
        {
            mMediaPlayer.setDataSource(videoUrl[playIndex++ % 2]);
            mMediaPlayer.prepareAsync();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {

    }

    @Override
    protected void onDestroy()
    {
        if (null != mMediaPlayer)
        {
            mMediaPlayer.reset();
        }
        super.onDestroy();
    }
}
