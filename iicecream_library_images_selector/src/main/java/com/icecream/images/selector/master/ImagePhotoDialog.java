package com.icecream.images.selector.master;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


/**
 * 拍照 相册 一张 选择对话框
 * Created by admin on 2016/11/10.
 */
public class ImagePhotoDialog extends AlertDialog {

    private Button btn_camera;//相机
    private Button btn_picker;//相册
    private Button btn_cancel;//取消

    public ImagePhotoDialog(Context context) {
        super(context, R.style.ice_image_dialog_theme_style);//设置背景不变暗
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        // 设置布局
        setContentView(R.layout.ice_image_dialog_selector);
        btn_camera = (Button) window.findViewById(R.id.btn_camera);
        btn_picker = (Button) window.findViewById(R.id.btn_picker);
        btn_cancel = (Button) window.findViewById(R.id.about_btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        Window window = this.getWindow();
        window.getAttributes().alpha = 1.0f;
        window.getAttributes().dimAmount = 0.3f;
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.ice_image_dialog_animation_style);
        window.getAttributes().flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.getAttributes().x = 0;
        window.getAttributes().y = getScreenHeight(getContext());
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(params);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
    }

    //带事件显示对话框
    public void dialogShow(View.OnClickListener cameraListener, View.OnClickListener pickerListener) {
        this.show();
        btn_camera.setOnClickListener(cameraListener);
        btn_picker.setOnClickListener(pickerListener);

    }
    /**
     * 获得屏幕宽度
     */
    private   int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
