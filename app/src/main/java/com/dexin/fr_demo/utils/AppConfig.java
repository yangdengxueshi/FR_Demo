package com.dexin.fr_demo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;

import java.util.Objects;


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
    @Nullable
    public static Bitmap decodeImage(String filePath) {
        try {
            //"旋转"和"缩放"
            Matrix lMatrix = new Matrix();//图像矩阵
            int orientation = new ExifInterface(filePath).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    lMatrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    lMatrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    lMatrix.postRotate(270);
                    break;
                default:
            }

            BitmapFactory.Options lBitmapFactoryOptions = new BitmapFactory.Options();
            lBitmapFactoryOptions.inSampleSize = 1;
            lBitmapFactoryOptions.inJustDecodeBounds = false;
//            lBitmapFactoryOptions.inMutable = true;
            Bitmap lBitmap = BitmapFactory.decodeFile(filePath, lBitmapFactoryOptions);
            Bitmap lTempBitmap = Bitmap.createBitmap(lBitmap, 0, 0, lBitmap.getWidth(), lBitmap.getHeight(), lMatrix, true);
            if (!Objects.equals(lBitmap, lTempBitmap)) lBitmap.recycle();
            Logger.t(TAG).d("decodeImage: " + "检查目标图像 " + lTempBitmap.getWidth() + "×" + lTempBitmap.getHeight());
            return lTempBitmap;
        } catch (Exception e) {
            Logger.t(TAG).e(e, "decodeImage: ");
        }
        return null;

    }
}
