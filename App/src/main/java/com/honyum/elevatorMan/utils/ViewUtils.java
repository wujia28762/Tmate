package com.honyum.elevatorMan.utils;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import static com.baidu.navisdk.util.common.ScreenUtil.dip2px;
import static com.baidu.navisdk.util.common.ScreenUtil.getScreenWidth;

/**
 * Created by star on 2018/3/31.
 */

public class ViewUtils {

    /**
     * 调整FrameLayout大小
     *
     * @param tp
     */
    public static void resizePikcer(FrameLayout tp) {
        float[] size = null;
        //npList size==3 代表 datepicker 年月日宽度对应为 0.25f 0.2f 0.2f
        //npList size==2 代表 timepicker 时分宽度对应为 0.175f 0.175f
        List npList = findNumberPicker(tp);
        if (npList.size() == 3) {
            size = new float[]{0.25f, 0.2f, 0.2f};
        } else if (npList.size() == 2) {
            size = new float[]{0.175f, 0.175f};

        }
        for (int i = 0; i < npList.size(); i++) {
            NumberPicker np = (NumberPicker) npList.get(i);
            resizeNumberPicker(np, size[i]);
        }
    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private static List findNumberPicker(ViewGroup viewGroup) {

        List npList = new ArrayList();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    /**
     * 调整numberpicker大小
     * @param np
     * @param size 每个numberPicker对应分得屏幕宽度
     */
    private static void resizeNumberPicker(NumberPicker np, float size) {
        int dp5 = dip2px(np.getContext(), 5);
        int dp2 = dip2px(np.getContext(), 2);
        //timepicker 时 分 左右各自有8dp空白
        int dp32 = dip2px(np.getContext(), 32);
        //屏幕宽度 - timepicker左右空白 -自设周边5dp空白
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( (int) ((getScreenWidth(np.getContext()) - dp32 - dp5 * 10) *size), ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0,0);
        np.setLayoutParams(params);
    }
}
