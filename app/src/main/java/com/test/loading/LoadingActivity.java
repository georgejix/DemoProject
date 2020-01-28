package com.test.loading;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.mplanet.testhandler.R;

@ContentView(R.layout.activity_loading)
public class LoadingActivity extends BaseActivity
{

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @OnClick(value = {R.id.show, R.id.hide})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.show:
                dialog = createLoadingDialog(this, "loading", true);
                break;
            case R.id.hide:
                closeDialog(dialog);
                break;
        }
    }

    public static Dialog createLoadingDialog(Context context, String msg, boolean canCancel)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);// 得到加载view
        TextView tipTextView = (TextView) v.findViewById(R.id.textview_content);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context);// 创建自定义样式dialog
        loadingDialog.setCancelable(canCancel); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(v);// 设置布局

        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 0.7f;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        loadingDialog.show();

        return loadingDialog;
    }

    public static void closeDialog(Dialog dialog)
    {
        if (dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

}
