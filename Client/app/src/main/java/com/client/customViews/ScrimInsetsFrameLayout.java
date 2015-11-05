package com.client.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.client.R;

/**
 * Created by Minh Toan on 04/11/2015.
 */
public class ScrimInsetsFrameLayout extends FrameLayout{

    private Drawable mInsetForeGround;

    private Rect mInsets;
    private final Rect mTempRect = new Rect();
    private OnInsetsCallback mOnInsetsCallback;

    public ScrimInsetsFrameLayout(Context context){
        super(context);
        init(context,null,0);
    }

    public ScrimInsetsFrameLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context,attrs, 0);
    }

    public ScrimInsetsFrameLayout (Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init(context, attrs, 0);
    }

    private void init (Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrimInsetsView, defStyle, 0);
        if (a == null){
            return;
        }

        mInsetForeGround = a.getDrawable(R.styleable.ScrimInsetsView_insetForeground);
        a.recycle();

        setWillNotDraw(true);
    }

    @Override
    protected boolean fitSystemWindows (Rect insets){
        mInsets = new Rect(insets);
        setWillNotDraw(mInsetForeGround == null);
        ViewCompat.postInvalidateOnAnimation(this);
        if(mOnInsetsCallback != null){
            mOnInsetsCallback.onInsetsChanged(insets);
        }

        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsetForeGround != null){
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            //Top
            mTempRect.set(0, 0, width, mInsets.top);
            mInsetForeGround.setBounds(mTempRect);
            mInsetForeGround.draw(canvas);

            //Bottom
            mTempRect.set(0, height - mInsets.bottom, width, height);
            mInsetForeGround.setBounds(mTempRect);
            mInsetForeGround.draw(canvas);

            //Left
            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
            mInsetForeGround.setBounds(mTempRect);
            mInsetForeGround.draw(canvas);

            //Right
            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
            mInsetForeGround.setBounds(mTempRect);
            mInsetForeGround.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow(){
        super.onAttachedToWindow();
        if (mInsetForeGround != null){
            mInsetForeGround.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsetForeGround != null) {
            mInsetForeGround.setCallback(null);
        }
    }


    /**
     * Allows the calling container to specify a callback for custom processing when insets change (i.e. when
     * {@link #fitSystemWindows(Rect)} is called. This is useful for setting padding on UI elements based on
     * UI chrome insets (e.g. a Google Map or a ListView). When using with ListView or GridView, remember to set
     * clipToPadding to false.
     */

    public void setOnInsetsCallback(OnInsetsCallback onInsetsCallback) {
        mOnInsetsCallback = onInsetsCallback;
    }

    public interface OnInsetsCallback {
        void onInsetsChanged(Rect insets);
    }
}


