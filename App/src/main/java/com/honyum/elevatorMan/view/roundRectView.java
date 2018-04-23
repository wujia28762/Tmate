package com.honyum.elevatorMan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by star on 2018/4/9.
 */

public class roundRectView extends View {


    private String disText;
    private int disTextColor;
    private int disTextSize;
    private int TextBackGround;
    private int roundSize;
    private Rect textRange;
    private Paint textPaint;


    public roundRectView(Context context) {
        this(context,null);
    }

    private void initPaint()
    {


    }
    public roundRectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);

    }

    public roundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //context.getTheme().obtainStyledAttributes(attrs,R.styleable.)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawRoundRect();
    }
}
