package com.haibin.calendarviewproject.mark;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;
import com.haibin.calendarviewproject.R;

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @since 2022/05/07
 */
public class MarkWeekView extends WeekView {

    MarkProperty markProperty = new MarkProperty();

    public MarkWeekView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        super.onPreviewHook();
        markProperty.onPreviewHook(this);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
        float cx = x + mItemWidth / 2f;
        float cy = mItemHeight / 2f + markProperty.getOffset();
        canvas.drawCircle(cx, cy, markProperty.schemeRadius, mSelectedPaint);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {
        float cx = x + mItemWidth / 2f;
        float cy = mItemHeight / 2f + markProperty.getOffset();
        if (!calendar.isCurrentMonth()) {
            mSchemePaint.setColor(markProperty.otherMonthSchemeColor);
        }
        canvas.drawCircle(cx, cy, markProperty.schemeRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        float y = 0;
        float baselineY = mTextBaseLine + y + markProperty.getOffset();
        float cx = x + mItemWidth / 2f;

        Paint paint;
        if (hasScheme || isSelected) {
            paint = mSchemeTextPaint;
            if (calendar.isCurrentMonth()) {
                paint.setColor(mDelegate.getSchemeTextColor());
            } else {
                paint.setColor(markProperty.otherMonthSchemeTextColor);
            }
        } else if (calendar.isCurrentMonth()) {
            paint = mCurMonthTextPaint;
        } else {
            paint = mOtherMonthTextPaint;
        }

        canvas.drawText(String.valueOf(calendar.getDay()),
                cx,
                baselineY,
                paint);

        if (markProperty.drawMonthText) {
            //绘制月份提示
            if (calendar.getDrawIndex() == 0 || calendar.getDay() == 1) {
                String monthText = getResources()
                        .getStringArray(R.array.month_string_array)[calendar.getMonth() - 1];
                if (calendar.isCurrentMonth()) {
                    markProperty.monthTextPaint.setColor(mCurMonthTextPaint.getColor());
                } else {
                    markProperty.monthTextPaint.setColor(mOtherMonthTextPaint.getColor());
                }
                canvas.drawText(monthText,
                        cx,
                        y - markProperty.monthTextPaint.ascent(),
                        markProperty.monthTextPaint);
            }
        }

        //绘制横线提示
        if (calendar.isCurrentDay()) {
            markProperty.lineRect.set(cx - markProperty.currentDayLineWidth / 2,
                    y + mItemHeight - markProperty.currentDayLineHeight,
                    cx + markProperty.currentDayLineWidth / 2,
                    y + mItemHeight);
            canvas.drawRoundRect(markProperty.lineRect,
                    MarkProperty.dpToPx(getContext(), 2),
                    MarkProperty.dpToPx(getContext(), 2),
                    mCurDayTextPaint);
        }
    }
}
