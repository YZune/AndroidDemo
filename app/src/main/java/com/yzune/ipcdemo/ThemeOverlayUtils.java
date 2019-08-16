package com.yzune.ipcdemo;

import android.app.Activity;
import androidx.annotation.StyleRes;

import java.util.Arrays;

/**
 * Utils for theme overlays.
 */
public abstract class ThemeOverlayUtils {

    @StyleRes
    private static int[] themeOverlays = new int[0];

    public static void setThemeOverlays(Activity activity, @StyleRes int... themeOverlays) {
        if (!Arrays.equals(ThemeOverlayUtils.themeOverlays, themeOverlays)) {
            ThemeOverlayUtils.themeOverlays = themeOverlays;
            activity.recreate();
        }
    }

    public static void clearThemeOverlays(Activity activity) {
        setThemeOverlays(activity);
    }

    @StyleRes
    public static int[] getThemeOverlays() {
        return themeOverlays;
    }

    public static void applyThemeOverlays(Activity activity) {
        for (int themeOverlay : themeOverlays) {
            activity.setTheme(themeOverlay);
        }
    }
}
