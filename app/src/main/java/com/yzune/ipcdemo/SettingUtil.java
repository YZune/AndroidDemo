package com.yzune.ipcdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

public class SettingUtil {
    private final static int HUAWEI_PHONEMODEL = 1;
    private final static int XIAOMI_PHONEMODEL = 2;
    private final static int VIVO_PHONEMODEL = 3;
    private final static int OPPO_PHONEMODEL = 4;
    private final static int SAMSUNG_PHONEMODEL = 5;
    private final static int DEFAULT_PHONEMODEL = 0;

    public static int getDeviceType() {
        int phoneModel;
        String deviceBrand = android.os.Build.BRAND;
        System.out.println(" deviceBrand : " + deviceBrand);
        if (!TextUtils.isEmpty(deviceBrand)) {
            if ("honor".equals(deviceBrand.toLowerCase()) || "huawei".equals(deviceBrand.toLowerCase())) {
                phoneModel = 1;
            } else if ("xiaomi".equals(deviceBrand.toLowerCase())) {
                phoneModel = 2;
            } else if ("vivo".equals(deviceBrand.toLowerCase())) {
                phoneModel = 3;
            } else if ("oppo".equals(deviceBrand.toLowerCase())) {
                phoneModel = 4;
            } else if ("samsung".equals(deviceBrand.toLowerCase())) {
                phoneModel = 5;
            } else {
                phoneModel = 0;
            }
        } else {
            phoneModel = 0;
        }
        return phoneModel;
    }

    public static void onViewClicked(int phoneModel, Context context) {
        Intent intent = new Intent();
        ComponentName comp = null;
        switch (phoneModel) {
            case HUAWEI_PHONEMODEL:
                if (Build.VERSION.SDK_INT >= 28) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else if (Build.VERSION.SDK_INT >= 26) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");
                } else if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                } else {
                    comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.com.huawei.permissionmanager.ui.MainActivity");
                }
                break;
            case XIAOMI_PHONEMODEL:
                comp = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                break;
            case VIVO_PHONEMODEL:
                if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity");
                } else {
                    comp = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity");
                }
                break;
            case OPPO_PHONEMODEL:
                if (Build.VERSION.SDK_INT >= 23) {
                    comp = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                } else {
                    comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity");
                }
                break;
            case SAMSUNG_PHONEMODEL:
                comp = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm_cn.com.samsung.android.sm.ui.ram.AutoRunActivity");
                break;
            case DEFAULT_PHONEMODEL:
                comp = null;
                break;
        }
        try {
            if (comp == null) {
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } else {
                intent.setComponent(comp);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            Intent intentSetting = new Intent();
            intentSetting.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intentSetting.setData(uri);
            context.startActivity(intentSetting);
        }
    }
}