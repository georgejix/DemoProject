package com.test.dialogactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_dialog)
public class DialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        setFinishOnTouchOutside(false);
    }

    @OnClick(value = {R.id.textview_close})
    private void onClick(View view){
        switch (view.getId()){
            case R.id.textview_close:
                finish();
            break;
        }
    }
}
