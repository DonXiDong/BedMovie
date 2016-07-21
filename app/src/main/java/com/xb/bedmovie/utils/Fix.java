package com.xb.bedmovie.utils;

import android.os.Environment;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by sb.wang on 2016/7/6.
 */
public class Fix {
    public void fix(){
        try {
//            PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
//            String hackFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +"/AntilazyLoad.dex"; // AntilazyLoad.dex的路径
//            Object a = combineArray(getDexElements(getPathList(pathClassLoader)), // 原有的 dex
//                    getDexElements(getPathList(new DexClassLoader(hackFilePath, getDir("dex", 0).getAbsolutePath(), hackFilePath, getClassLoader()))));  // 将新的dex插入到dexElements数组的前面
//            Object a2 = getPathList(pathClassLoader);
//            setField(a2, a2.getClass(), "dexElements", a); /f/ 通过反射修改dexElements数组
//            pathClassLoader.loadClass("xyz.hanks.Hack");
//
//
//            // 加载补丁包
//            String patchFilePath = Environment.getExternalStorageDirectory()
//                    .getAbsolutePath() + "/patch.dex";
//            Object a3 = combineArray(getDexElements(getPathList(pathClassLoader)), // 原有的 dex
//                    getDexElements(getPathList(new DexClassLoader(patchFilePath, getDir("dex", 0).getAbsolutePath(), patchFilePath, getClassLoader()))));
//            Object a4 = getPathList(pathClassLoader);
//            setField(a4, a4.getClass(), "dexElements", a3);
//            pathClassLoader.loadClass("xyz.hanks.fix.BugClass");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
