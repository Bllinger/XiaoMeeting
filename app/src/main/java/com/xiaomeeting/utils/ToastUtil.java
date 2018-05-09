package com.xiaomeeting.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Blinger on 2018/5/8.
 */

public class ToastUtil {
    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
