package com.master.design.eighty.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CircleLinearLayout extends LinearLayout {
    private float strokeWidth;
    Paint circlePaint;
    int strokeColor, solidColor;
    String Text;
    Float size = -1f;

    public CircleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
        setWillNotDraw(false);

    }

    private void initViews(Context context, AttributeSet attrs) {

        //paint object for drawing in onDraw
        circlePaint = new Paint();

    }

    @Override
    public void draw(Canvas canvas) {
        final int diameter, radius, h, w;

        circlePaint.setColor(solidColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //get Height and Width
        h = this.getHeight();
        w = this.getWidth();

        diameter = ((h > w) ? h : w);
        radius = diameter / 2;

        //setting Height and width
        this.setMinimumHeight(diameter);
        this.setMinimumWidth(diameter);

        canvas.drawCircle(diameter / 2, diameter / 2, radius - strokeWidth, circlePaint);
        super.draw(canvas);
    }

}