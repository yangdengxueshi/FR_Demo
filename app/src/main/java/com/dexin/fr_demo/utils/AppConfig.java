package com.dexin.fr_demo.utils;

import android.graphics.Bitmap;

import com.orhanobut.logger.Logger;


/**
 * App配置类
 */
public final class AppConfig {
    private static final String TAG = "TAG_AppConfig";

    /**
     * 根据文件路径解码图片
     *
     * @param filePath 文件路径
     * @return Bitmap
     */
    public static Bitmap decodeImage(String filePath) {
        try {

        } catch (Exception e) {
            Logger.t(TAG).e(e, "decodeImage: ");
        }
        return null;

    }
}
