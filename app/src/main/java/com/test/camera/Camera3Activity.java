package com.test.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.App;
import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//camera1方式的一些功能demo
@ContentView(R.layout.activity_camera3)
public class Camera3Activity extends BaseActivity implements MediaRecorder.OnInfoListener, Handler.Callback {
    private final static String TAG = "Camera2Activity";

    @ViewInject(R.id.layout_preview)
    private SurfaceView mPreviceSurface;

    private int mBackCameraId,mFrontCameraId,mCurrentCameraId;
    private int mBackCameraOrientation,mFrontCameraOrientation, mCurrentCameraOrientation;
    private Camera mCamera;
    private SurfaceCallback mSurfaceCallback;
    private static Object mOpenCameraLock = new Object();
    private int mScreenDegree = 0;
    private LocalAutoFocusCallback mAutoFocusCallback;
    private Handler mBackHandler;

    private final static int START_RECORD = 1;
    private final static int STOP_RECORD = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        getCameraParams();
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(null != mCamera){
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null != mCamera){
            mCamera.startPreview();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mCamera){
            mCamera.release();
        }
    }

    //获取方向数值
    private void prepareOpenCamera(boolean isback){
        if(isback){
            mCurrentCameraOrientation = (mBackCameraOrientation - mScreenDegree + 360) % 360;
        }else{
            mCurrentCameraOrientation = (mFrontCameraOrientation - mScreenDegree + 180 + 360) % 360;
        }
    }

    //打开摄像头
    private void openCamera(){
        Log.d(TAG, "openCamera");
        if(null != mCamera){
            mCamera.stopPreview();
            mCamera.release();
        }
        mCamera = Camera.open(mCurrentCameraId);
        mCamera.setDisplayOrientation(mCurrentCameraOrientation);

        if(null == mSurfaceCallback) {
            mSurfaceCallback = new SurfaceCallback();
            mPreviceSurface.getHolder().addCallback(mSurfaceCallback);
        }else {
            resizePreviewSize();
            mCamera.startPreview();
            try {
                mCamera.setPreviewDisplay(mPreviceSurface.getHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(value = {R.id.textview_facing, R.id.textview_focus, R.id.textview_flash, R.id.textview_scene,
     R.id.textview_takephoto, R.id.textview_takevideo, R.id.textview_resolution, R.id.textview_fps})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.textview_fps:
                break;
            case R.id.textview_resolution:
                break;
            case R.id.textview_takevideo:
                takeVideo();
                break;
            case R.id.textview_takephoto:
                takePhoto();
                break;
            case R.id.textview_scene:
                toggleScene();
                break;
            case R.id.textview_flash:
                toggleFlash();
                break;
            case R.id.textview_focus:
                toggleFocus();
                break;
            case R.id.textview_facing:
                toggleFacing();
                break;
        }
    }

    private void takeVideo(){
        if(!isRecording){
            mBackHandler.sendEmptyMessage(START_RECORD);
        }else {
            mBackHandler.sendEmptyMessage(STOP_RECORD);
        }
    }

    private MediaRecorder mediaRecorder;
    private boolean isRecording = false;
    protected boolean preparemediaRecorder() {
        if(null == mCamera)
            return false;
        mCamera.lock();
        mCamera.unlock();
        mediaRecorder = new MediaRecorder();
        try {
            mediaRecorder.setCamera(mCamera);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

            CamcorderProfile camcorderProfile = CamcorderProfile.get(mCurrentCameraId, CamcorderProfile.QUALITY_720P);

            //输出格式
            mediaRecorder.setOutputFormat(camcorderProfile.fileFormat);
            //视频帧率
            mediaRecorder.setVideoFrameRate(camcorderProfile.videoFrameRate);
            //视频大小
            mediaRecorder.setVideoSize(mPreviceSurface.getHeight(), mPreviceSurface.getWidth());
            //视频比特率
            mediaRecorder.setVideoEncodingBitRate(camcorderProfile.videoBitRate);
            //视频编码器
            mediaRecorder.setVideoEncoder(camcorderProfile.videoCodec);

            //音频编码率
            mediaRecorder.setAudioEncodingBitRate(camcorderProfile.audioBitRate);
            //音频声道
            mediaRecorder.setAudioChannels(camcorderProfile.audioChannels);
            //音频采样率
            mediaRecorder.setAudioSamplingRate(camcorderProfile.audioSampleRate);
            //音频编码器
            mediaRecorder.setAudioEncoder(camcorderProfile.audioCodec);

            //输出路径
            mediaRecorder.setOutputFile(App.getFile() + File.separator + System.currentTimeMillis() + ".mp4");

            /*//设置视频输出的最大尺寸
            if (mCameraConfigProvider.getVideoFileSize() > 0) {
                mediaRecorder.setMaxFileSize(mCameraConfigProvider.getVideoFileSize());
                mediaRecorder.setOnInfoListener(this);
            }

            //设置视频输出的最大时长
            if (mCameraConfigProvider.getVideoDuration() > 0) {
                mediaRecorder.setMaxDuration(mCameraConfigProvider.getVideoDuration());
                mediaRecorder.setOnInfoListener(this);
            }*/
            mediaRecorder.setOrientationHint(mBackCameraId == mCurrentCameraId ? mCurrentCameraOrientation
                    : 180 + mCurrentCameraOrientation);

            //准备
            mediaRecorder.prepare();

            return true;
        } catch (IllegalStateException error) {
            Log.e(TAG, "IllegalStateException preparing MediaRecorder: " + error.getMessage());
        } catch (IOException error) {
            Log.e(TAG, "IOException preparing MediaRecorder: " + error.getMessage());
        } catch (Throwable error) {
            Log.e(TAG, "Error during preparing MediaRecorder: " + error.getMessage());
        }
        releaseVideoRecorder();
        return false;
    }

    protected void releaseVideoRecorder() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.reset();
                mediaRecorder.release();
            }
        } catch (Exception ignore) {

        } finally {
            mediaRecorder = null;
        }
    }

    private void takePhoto(){
        if(null == mCamera)
            return;
        mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                String photoPath = App.getFile() + File.separator + System.currentTimeMillis() + ".jpg";
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(new File(photoPath));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Matrix matrix = new Matrix();
                    matrix.setRotate(mBackCameraId == mCurrentCameraId ? mCurrentCameraOrientation
                            : 180 + mCurrentCameraOrientation);
                    Bitmap bitmap_ = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                            bitmap.getHeight(), matrix, true);
                    bitmap_.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if(null != fos){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                camera.startPreview();
            }
        });
    }

    private void toggleScene(){
        if(null == mCamera)
            return;
        List<String> sceneList = mCamera.getParameters().getSupportedSceneModes();
        if(null == sceneList)
            return;
        Camera.Parameters parameters = mCamera.getParameters();
        for(int i = 0; i < sceneList.size(); i++){
            if(parameters.getSceneMode().equals(sceneList.get(i))){
                String flashStr = sceneList.get((i + 1) % sceneList.size());
                parameters.setSceneMode(flashStr);
                showToast(flashStr);
                break;
            }
        }
        mCamera.setParameters(parameters);
    }


    //设置闪光灯模式
    private void toggleFlash(){
        if(null == mCamera)
            return;
        List<String> flashList = mCamera.getParameters().getSupportedFlashModes();
        if(null == flashList)
            return;
        Camera.Parameters parameters = mCamera.getParameters();
        for(int i = 0; i < flashList.size(); i++){
            if(parameters.getFlashMode().equals(flashList.get(i))){
                String flashStr = flashList.get((i + 1) % flashList.size());
                parameters.setFlashMode(flashStr);
                showToast(flashStr);
                break;
            }
        }
        mCamera.setParameters(parameters);
    }

    //设置区域聚焦
    private void focusArea(float rawx, float rawy){
        if(mFrontCameraId == mCurrentCameraId)
            return;
        //左上角(-1000,-1000) 右下角(1000,1000)
        float x = rawx - mPreviceSurface.getWidth() / 2;
        float y = rawy - mPreviceSurface.getHeight() / 2;
        x = 1000 * x * 2 / mPreviceSurface.getWidth();
        y = 1000 * y * 2 / mPreviceSurface.getHeight();
        if(null == mCamera)
            return;
        final Camera.Parameters parameters = mCamera.getParameters();
        float lx = x - 50;
        float ly = y - 50;
        float rx = x + 50;
        float ry = y + 50;
        if(lx < -1000) lx = -1000;
        if(ly < -1000) ly = -1000;
        if(rx > 1000) rx = 1000;
        if(ry > 1000) ry = 1000;
        Camera.Area area = new Camera.Area(new Rect((int)lx, (int)ly, (int)rx, (int)ry), 800);
        List<Camera.Area> list = new ArrayList<>();
        list.add(area);
        parameters.setFocusAreas(list);
        final String currentFocusModel = parameters.getFocusMode();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        mCamera.setParameters(parameters);
        mCamera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                //parameters.setFocusMode(currentFocusModel);
                //mCamera.setParameters(parameters);
            }
        });
    }

    //切换聚焦模式
    private void toggleFocus(){
        if(null == mCamera)
            return;
        List<String> mFocusList = mCamera.getParameters().getSupportedFocusModes();
        if(null == mFocusList)
            return;
        if(null == mAutoFocusCallback){
            mAutoFocusCallback = new LocalAutoFocusCallback();
        }
        Camera.Parameters parameters = mCamera.getParameters();
        for(int i = 0; i < mFocusList.size(); i++){
            if(parameters.getFocusMode().equals(mFocusList.get(i))){
                String focus = mFocusList.get((i + 1) % mFocusList.size());
                parameters.setFocusMode(focus);
                showToast(focus);
                mCamera.cancelAutoFocus();
                if(Camera.Parameters.FOCUS_MODE_AUTO.equals(focus)){
                    mCamera.autoFocus(mAutoFocusCallback);
                }
                break;
            }
        }
        mCamera.setParameters(parameters);
    }

    //切换前后摄像头
    private void toggleFacing(){
        synchronized (mOpenCameraLock){
            if(mBackCameraId == mCurrentCameraId){
                mCurrentCameraId = mFrontCameraId;
                prepareOpenCamera(true);
            }else{
                mCurrentCameraId = mBackCameraId;
                prepareOpenCamera(false);
            }
            openCamera();
        }
    }

    //设置全屏
    private void setFullScreen(){
        int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(options);
    }

    //获取前后摄像头
    private void getCameraParams(){
        int cameraNumber = Camera.getNumberOfCameras();
        Log.d(TAG, "cameranumber=" + cameraNumber);
        for(int i = 0; i < cameraNumber; i++){
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if(null == cameraInfo)
                continue;
            if(Camera.CameraInfo.CAMERA_FACING_BACK == cameraInfo.facing){
                mBackCameraId = i;
                mBackCameraOrientation = cameraInfo.orientation;
            }else if(Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing){
                mFrontCameraId = i;
                mFrontCameraOrientation = cameraInfo.orientation;
            }
        }
    }

    private void init(){
        synchronized (mOpenCameraLock) {
            mCurrentCameraId = mBackCameraId;
        }
        prepareOpenCamera(true);
        openCamera();
        mPreviceSurface.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        focusArea(event.getX(), event.getY());
                        break;
                }
                return true;
            }
        });
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mBackHandler = new Handler(handlerThread.getLooper(), this);
    }

    class SurfaceCallback implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated");
            if(null != mCamera){
                resizePreviewSize();
                mCamera.startPreview();
                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    Log.d(TAG, e + "");
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d(TAG, "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG, "surfaceDestroyed");
        }
    }

    private void resizePreviewSize(){
        if(null == mCamera) {
            return;
        }
        int width = mPreviceSurface.getHeight();
        int height = mPreviceSurface.getWidth();
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        int previewWidth = 0,previewHeight = 0;
        Log.d(TAG, "view.size=" + width + "," + height);
        for(Camera.Size size : sizeList){
            if(null == size)
                continue;
            if(size.width <= width && size.height <= height){
                if(size.width > previewWidth || size.height > previewHeight){
                    previewHeight = size.height;
                    previewWidth = size.width;
                }
            }
        }
        mPreviceSurface.setLayoutParams(new FrameLayout.LayoutParams(previewHeight, previewWidth));
        parameters.setPreviewSize(previewWidth, previewHeight);
        Log.d(TAG, "preview.size=" + previewWidth + "," + previewHeight);
        mCamera.setParameters(parameters);
    }

    class LocalAutoFocusCallback implements Camera.AutoFocusCallback{

        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            Log.d(TAG, "autofocus");
            if(success){
                mPreviceSurface.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(null != mCamera) {
                            mCamera.autoFocus(mAutoFocusCallback);
                        }
                    }
                }, 1000);
            }
        }
    }

    private void showToast(String str){
        if(null == str)
            return;
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfo(MediaRecorder mr, int what, int extra) {
        if (MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED == what) {
            //到达最大时长
        } else if (MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED == what) {
            //到达最大尺寸
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case START_RECORD:
                if(preparemediaRecorder()) {
                    mediaRecorder.start();
                    isRecording = true;
                    showToast("start recording");
                }
                break;
            case STOP_RECORD:
                mediaRecorder.setOnErrorListener(null);
                mediaRecorder.setOnInfoListener(null);
                mediaRecorder.setPreviewDisplay(null);
                mediaRecorder.stop();
                releaseVideoRecorder();
                isRecording = false;
                showToast("end recording");
                break;
        }
        return true;
    }
}
