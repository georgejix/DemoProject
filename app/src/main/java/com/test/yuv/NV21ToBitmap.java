package com.test.yuv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.util.Log;

public class NV21ToBitmap {
    private RenderScript rs;
    private ScriptIntrinsicYuvToRGB yuvToRgbIntrinsic;
    private Type.Builder yuvType, rgbaType;
    private Allocation in, out;
    private static final int YUV_WIDTH = 1280;
    private static final int YUV_HIGHT = 720;
    Matrix matrix;
    public NV21ToBitmap(Context context, int imageViewWidth, int imageViewHeight) {
        rs = RenderScript.create(context);
        yuvToRgbIntrinsic = ScriptIntrinsicYuvToRGB.create(rs, Element.U8_4(rs));
        float scaleWidth = ((float) imageViewWidth) / YUV_WIDTH;
        float scaleHeight = ((float) imageViewHeight) / YUV_HIGHT;
        matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
    }
    public Bitmap nv21ToBitmap(byte[] nv21){
        if (yuvType == null){
            yuvType = new Type.Builder(rs, Element.U8(rs)).setX(nv21.length);
            in = Allocation.createTyped(rs, yuvType.create(), Allocation.USAGE_SCRIPT);
            rgbaType = new Type.Builder(rs, Element.RGBA_8888(rs)).setX(YUV_WIDTH).setY(YUV_HIGHT);
            out = Allocation.createTyped(rs, rgbaType.create(), Allocation.USAGE_SCRIPT);
        }
        in.copyFrom(nv21);
        yuvToRgbIntrinsic.setInput(in);
        yuvToRgbIntrinsic.forEach(out);
        Bitmap bmpout = Bitmap.createBitmap(YUV_WIDTH, YUV_HIGHT, Bitmap.Config.ARGB_8888);
        out.copyTo(bmpout);
        Bitmap newImage = scaleBitmap(bmpout,matrix);
        return newImage;
    }

    private Bitmap scaleBitmap(Bitmap origin, Matrix matrix) {
        if (origin == null) {
            return null;
        }

        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, YUV_WIDTH, YUV_HIGHT, matrix, false);
        if (!origin.isRecycled()) {
            origin.recycle();
            origin = null;
        }
        return newBM;
    }
}

