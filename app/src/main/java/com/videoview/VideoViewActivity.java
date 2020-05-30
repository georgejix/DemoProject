package com.videoview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_video_view)
public class VideoViewActivity extends BaseActivity
{

    @ViewInject(R.id.videoView)
    VideoView mVideoView;

    private String video1 = "https://img.1000.com/qm-a-img/prod/2775370/967d971876916b079401f6761d1de54f.mp4";

    private String video2 = "https://img.1000.com/qm-a-img/prod/2775370/89fddf6be17ffde4b77f308bbe5eaf11.mp4";

    private String videoUrl[] = new String[2];

    private int playIndex = 0;

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
        mVideoView.setVideoURI(Uri.parse(videoUrl[playIndex++ % 2]));
        mVideoView.setOnPreparedListener(mp -> mVideoView.start());
        mVideoView.setOnCompletionListener(mp -> mVideoView.setVideoURI(Uri.parse(videoUrl[playIndex++ % 2])));
    }

    @Override
    protected void onDestroy()
    {
        if (null != mVideoView)
        {
            mVideoView.suspend();
        }
        super.onDestroy();
    }
}
