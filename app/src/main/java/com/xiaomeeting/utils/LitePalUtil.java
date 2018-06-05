package com.xiaomeeting.utils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by Blinger on 2018/5/15.
 */

public class LitePalUtil {
    private final String TAG = LogUtil.createTag(this);

    public static void deleteData(Class object) {
        DataSupport.deleteAll(object);
    }

    public static List<?> findData(Object object) {
        List<?> list = DataSupport.findAll(object.getClass());
        if (list.size() != 1) {
            LogUtil.e("LitePalUtil___findData", "数据库数据不止一条");
            return null;
        } else {
            return list;
        }
    }
}
