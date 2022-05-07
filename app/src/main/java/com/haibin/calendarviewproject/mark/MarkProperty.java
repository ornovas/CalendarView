package com.haibin.calendarviewproject.mark;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;

import com.haibin.calendarview.BaseView;

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @since 2022/05/07
 */
public class MarkProperty {

    /**
     * 半径
     */
    int schemeRadius;

    /**
     * 是否需要绘制月份
     */
    boolean drawMonthText = true;

    /**
     * 绘制月份
     */
    Paint monthTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 其他月份圆圈的颜色
     */
    int otherMonthSchemeColor = Color.parseColor("#a4bff6");

    /**
     * 其他月份文本的颜色
     */
    int otherMonthSchemeTextColor = Color.parseColor("#bbd2ff");

    /**
     * 今天 横线的宽度
     */
    float currentDayLineWidth = -1f;

    /**
     * 今天 横线的高度
     */
    float currentDayLineHeight = -1f;

    RectF lineRect = new RectF();

    public MarkProperty() {
        monthTextPaint.setAntiAlias(true);
        monthTextPaint.setStyle(Paint.Style.FILL);
        monthTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    void onPreviewHook(BaseView baseView) {
        schemeRadius = Math.min(baseView.mItemWidth, baseView.mItemHeight) / 6 * 2;
        monthTextPaint.setTextSize(baseView.mCurMonthTextPaint.getTextSize() * 3 / 4);
        currentDayLineWidth = dpToPx(baseView.getContext(), 12);
        currentDayLineHeight = dpToPx(baseView.getContext(), 3);
    }

    float getOffset() {
        if (!drawMonthText) {
            return 0;
        }
        return -monthTextPaint.ascent() / 2;
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
