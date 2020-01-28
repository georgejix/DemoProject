package com.test.keyboardinput;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;
import com.test.util.CommonUtils;

@ContentView(R.layout.activity_input)
public class InputActivity extends Activity {
    private final String TAG = "InputActivity";

    @ViewInject(R.id.search)
    private EditText searchEdit;
    @ViewInject(R.id.change)
    private EditText changeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.d(TAG, "IME_ACTION_SEND");
                    CommonUtils.closeKeyboard(InputActivity.this, searchEdit);
                    handled = true;
                }
                return false;
            }
        });
    }

    @OnClick(value = {R.id.btn_change})
    private void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_change:
                modifyShowMode();
                break;
        }
    }

    private void modifyShowMode() {
        TransformationMethod method = changeEdit.getTransformationMethod();
        if (method instanceof HideReturnsTransformationMethod) {
            changeEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if (method instanceof PasswordTransformationMethod) {
            changeEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else{
            changeEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        changeEdit.setSelection(changeEdit.getText().toString().length());
        ImageView img = new ImageView(this);
    }
}
