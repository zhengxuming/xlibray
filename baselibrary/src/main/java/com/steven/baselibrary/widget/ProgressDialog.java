package com.steven.baselibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.steven.baselibrary.R;

/**
 * Created by Steven on 2017/9/6.
 */

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        this(context, R.style.ProgressDialog);
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogWidth();
    }

    private void initDialogWidth() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams wmLp = window.getAttributes();
            wmLp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(wmLp);
        }
    }

    public static class Builder{
       private Context context;

        public Builder(Context context){
            this.context=context;
        }

        public ProgressDialog create(){
            ProgressDialog dialog=new ProgressDialog(context);
            dialog.setContentView(R.layout.custom_loading_progress_layout);
            return dialog;
        }

    }

}
