package com.example.project.ultil;

import android.app.Dialog;
import android.content.Context;

import com.example.project.R;

public class Loading {
    private static Dialog dialog;
    public static void loading(Context context)
    {
        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading);
        dialog.show();
    }
    public static void destroyLoading()
    {
        dialog.cancel();
    }

}
