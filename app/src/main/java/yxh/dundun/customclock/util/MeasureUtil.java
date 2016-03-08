package yxh.dundun.customclock.util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by dundun on 16/3/2.
 */
public class MeasureUtil {
    public static WindowManager manager;
    public static DisplayMetrics metrics;
    public static int[] screenXY = {0, 0};

    /**
     * 获取屏幕宽高
     * @param activity
     * @return
     */
    public static int[] getScreenXY(Activity activity) {
        metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenXY[0] = metrics.widthPixels;
        screenXY[1] = metrics.heightPixels;

        return screenXY;
    }

//    public static int[] getScreenXY(Context context) {
//        manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        manager.getDefaultDisplay().getMetrics(metrics);
//        screenXY[0] = metrics.widthPixels;
//        screenXY[1] = metrics.heightPixels;
//        return screenXY;
//    }

}
