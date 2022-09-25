package com.pvting.customcontrols;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ToggleButton extends View {
    private boolean isOpen=false;
    private Bitmap backBitmap;
    private Bitmap frontBitmap;
    private int x;
    private boolean isTouching;

    public ToggleButton(Context context) {
        super(context);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setBackImg(int slide_button_back) {
        backBitmap = BitmapFactory.decodeResource(getResources(),slide_button_back);
    }

    public void setFrontImg(int slide_button_img) {
        frontBitmap = BitmapFactory.decodeResource(getResources(),slide_button_img);
    }

    public void setState(boolean b) {
        this.isOpen = b;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backBitmap.getWidth(),backBitmap.getHeight()*2);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(backBitmap,0,0,null);
        if(isTouching){
            if(x<frontBitmap.getWidth()/2){
                canvas.drawBitmap(frontBitmap,0,0,null);
            }else if(x>backBitmap.getWidth()-frontBitmap.getWidth()/2){
                canvas.drawBitmap(frontBitmap,backBitmap.getWidth()-frontBitmap.getWidth(),0,null);
            }else{
                canvas.drawBitmap(frontBitmap,x-frontBitmap.getWidth()/2,0,null);
            }
        }else {
            if(!isOpen){
                canvas.drawBitmap(frontBitmap,0,0,null);
            }else {
                canvas.drawBitmap(frontBitmap,backBitmap.getWidth()-frontBitmap.getWidth(),0,null);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x= (int) event.getX();

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isTouching = false;
                isOpen = x>backBitmap.getWidth()/2;
                break;

        }
        invalidate();
        return true;
    }
}
