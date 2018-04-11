package com.dexin.fr_demo.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;
import com.dexin.fr_demo.BuildConfig;
import com.dexin.fr_demo.bean.FaceDB;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.vondear.rxtools.RxTool;

import org.jetbrains.annotations.Contract;

import java.util.List;
import java.util.Objects;

/**
 * 自定义Application
 */
public class CustomApplication extends Application {
    private static Context sContext;//TODO 这里static所修饰的context并不会引起内存泄漏，因为static数据与单例context拥有相同的生命周期
    private FaceDB mFaceDB;//人脸数据库
    private Uri mImageUri;//图片Uri

    /**
     * 全局获取 Context对象
     *
     * @return 全局的 context对象
     */
    @Contract(pure = true)
    public static Context getContext() {
        return sContext;
    }

    public FaceDB getFaceDB() {
        return mFaceDB;
    }

    public void setFaceDB(FaceDB faceDB) {
        mFaceDB = faceDB;
    }

    public Uri getImageUri() {
        return mImageUri;
    }

    public void setImageUri(Uri imageUri) {
        mImageUri = imageUri;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Objects.equals(getCurrentProcessName(), BuildConfig.APPLICATION_ID) && !LeakCanary.isInAnalyzerProcess(this)) {//如果不在分析器进程中:此进程专注于LeakCanary堆分析,你不应该在此进程中初始化App
            LeakCanary.install(this);
            initMemberVar();//初始化成员变量
        }
    }

    private void initMemberVar() {
        sContext = getApplicationContext();
        Logger.addLogAdapter(new AndroidLogAdapter());
        RxTool.init(this);
        Utils.init(this);
        mFaceDB = new FaceDB(Objects.requireNonNull(getExternalCacheDir()).getPath());
    }

    /**
     * 获取当前进程的名字
     *
     * @return 当前进程名
     */
    private String getCurrentProcessName() {
        String currentProcessName = "";
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
            if ((runningAppProcessInfoList != null) && !runningAppProcessInfoList.isEmpty()) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfoList) {
                    if ((runningAppProcessInfo != null) && (runningAppProcessInfo.pid == android.os.Process.myPid())) {
                        currentProcessName = runningAppProcessInfo.processName;
                        break;
                    }
                }
            }
        }
        return currentProcessName;
    }
}
