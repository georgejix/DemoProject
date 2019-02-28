package com.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by jix on 2019/2/28.
 */

public class BookManagerService extends Service {
    private final String TAG = "BookManagerService";

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList =
            new CopyOnWriteArrayList<>();

    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListner(IOnNewBookArrivedListener listener) throws RemoteException {
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            }else {
                Log.d(TAG, "already exists");
            }
            Log.d(TAG, "mListenerList.size = " + mListenerList.size());
        }

        @Override
        public void unregisterListner(IOnNewBookArrivedListener listener) throws RemoteException {
            if(mListenerList.contains(listener)){
                mListenerList.remove(listener);
                Log.d(TAG, "unregister listener succeed");
            }else{
                Log.d(TAG, "not found, can not unregister");
            }
            Log.d(TAG, "mListenerList.size = " + mListenerList.size());
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }

    private void onNewBookArrived(Book book){
        mBookList.add(book);
        Log.d(TAG, "onNewBookArrived mListenerList.size = " + mListenerList.size());
        for(IOnNewBookArrivedListener listener : mListenerList){
            try {
                listener.onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            while(!mIsServiceDestoryed.get()){
                try{
                    Thread.sleep(5000);
                }catch (Exception e){}
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new book" + bookId);
                try{
                    onNewBookArrived(newBook);
                }catch (Exception e){}
            }
        }
    }
}
