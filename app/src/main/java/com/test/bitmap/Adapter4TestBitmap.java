package com.test.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mplanet.testhandler.R;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by jix on 2018/11/28.
 */

public class Adapter4TestBitmap extends BaseAdapter{
    private Context context;
    private File files[];

    public Adapter4TestBitmap(Context context, File f){
        this.context = context;
        this.files = f.listFiles();
    }

    @Override
    public int getCount() {
        return null == files ? 0 : files.length * 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item4testbitmapadapter, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(cancelPotentialWork(position, viewHolder.imageView)) {
            Bitmap bitmap = BitmapCache.getInstance().getBitmapFromMemCache(position + "");
            //Bitmap bitmap = BitmapCacheDisk.getInstance().getBitmapFromDiskCache(position + "");
            if(null == bitmap) {
                BitmapWorkerTask task = new BitmapWorkerTask(viewHolder.imageView);
                AsyncDrawable asyncDrawable = new AsyncDrawable(task);
                viewHolder.imageView.setImageDrawable(asyncDrawable);
                task.execute(position);
            }else{
                viewHolder.imageView.setImageBitmap(bitmap);
            }
        }
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
    }

    class BitmapWorkerTask extends AsyncTask{
        private WeakReference imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView){
            imageViewReference = new WeakReference(imageView);
        }

        @Override
        protected Bitmap doInBackground(Object[] params) {
            data = (int) params[0];
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(files[data % files.length].getAbsolutePath(), option);
            option.inSampleSize = option.outWidth / 100;
            option.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(files[data % files.length].getAbsolutePath(), option);
            BitmapCache.getInstance().addBitmapToMemoryCache(data + "", bitmap);
            //BitmapCacheDisk.getInstance().addBitmapToDiskCache(data + "", bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Object object) {
            Bitmap bitmap = (Bitmap) object;
            ImageView imageView = (ImageView) imageViewReference.get();
            BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
            if(this == bitmapWorkerTask && null != imageView){
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    class AsyncDrawable extends BitmapDrawable {
        private final WeakReference bitmapWorkerTaskReference;

        public AsyncDrawable(BitmapWorkerTask bitmapWorkerTask) {
            bitmapWorkerTaskReference =
                    new WeakReference(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return (BitmapWorkerTask) bitmapWorkerTaskReference.get();
        }
    }

    public boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}
