package com.test.observer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.BaseActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.mplanet.testhandler.R;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@ContentView(R.layout.activity_observer)
public class ObserverActivity extends BaseActivity
{

    private final static String TAG = "ObserverActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
    }

    @SuppressLint("CheckResult")
    private void initView()
    {
        test1();
    }

    private void test1()
    {
        Observer<String> observer = new Observer<String>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {

            }

            @Override
            public void onNext(String s)
            {
                Log.d(TAG, s);
            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onComplete()
            {
                Log.d(TAG, "onComplete");
            }
        };
        Observer<String> observer2 = new Observer<String>()
        {
            @Override
            public void onSubscribe(Disposable d)
            {

            }

            @Override
            public void onNext(String s)
            {
                Log.d(TAG, "2_" + s);
            }

            @Override
            public void onError(Throwable e)
            {

            }

            @Override
            public void onComplete()
            {
                Log.d(TAG, "2_onComplete");
            }
        };
        Observable<String> observable = Observable.create((ObservableOnSubscribe<String>) e -> {
            e.onNext("next1");
            e.onNext("next2");
            e.onComplete();
        });
        observable.subscribe(observer);
        observable
                .subscribeOn(Schedulers.io())    // 给上面代码分配异步线程
                .observeOn(AndroidSchedulers.mainThread()) // 给下面代码分配主线程;
                .subscribe(observer2);
    }
}