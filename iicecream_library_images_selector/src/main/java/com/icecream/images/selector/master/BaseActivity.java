package com.icecream.images.selector.master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;


/**
 * 所有activity的基数
 * Created by HuangJin on 2016/9/13.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // 不显示标题栏
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 不显示软键盘
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView(this);
        setData(this);
        setListener(this);


    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        // overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        //overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }


    @Override
    public void finish() {
        super.finish();
        // overridePendingTransition(R.anim.finish_turn_left, R.anim.finish_turn_right);
    }

    public abstract void initView(Context mContext);// 初始化

    public abstract void setListener(Context mContext);// 事件监听

    public abstract void setData(Context mContext);// 处理数据

    public abstract void back(View view);// 返回键在xml控件onClick()属性里添加


}
