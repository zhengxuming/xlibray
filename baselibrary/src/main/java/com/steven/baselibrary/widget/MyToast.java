package com.steven.baselibrary.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.steven.baselibrary.R;

public class MyToast {

    private Toast toast;

    private static class MyToastHolder {
        private static MyToast myToast = new MyToast();
    }

    public static MyToast getInstance() {
        return MyToastHolder.myToast;
    }

    private void show(Context ctx, String content, int toastIcon) {
        if (toast == null) {
            toast = new Toast(ctx);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            ViewGroup layout = (ViewGroup) LayoutInflater.from(ctx).inflate(R.layout.toast_dialog, null);
            toast.setView(layout);
        }
        View view = toast.getView();
        TextView tvToast = view.findViewById(R.id.toastText);
        ImageView ivToast = view.findViewById(R.id.iv_toastImage);
        ivToast.setBackgroundResource(toastIcon);
        tvToast.setText(content);
        toast.show();
    }

    public void showOk(Context ctx, String text) {
        show(ctx, text, R.mipmap.toast_ok);
    }

    public void showError(Context ctx, String text) {
        show(ctx, text, R.mipmap.toast_error);
    }

    public void showTip(Context ctx, String text) {
        show(ctx, text, R.mipmap.toast_tip);
    }
}
