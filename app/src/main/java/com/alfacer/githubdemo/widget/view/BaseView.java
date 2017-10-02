package com.alfacer.githubdemo.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by albert on 10/1/17.
 */

public abstract class BaseView extends FrameLayout {
    public BaseView(Context context) {
        super(context);
        InitializeFrameLayout();
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitializeFrameLayout();
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitializeFrameLayout();
    }


    protected void InitializeFrameLayout() {
        if (!isInEditMode()) {
            View view = inflate(getContext(), getLayoutResId(), null);
            removeAllViews();
            addView(view);

            if (super.getTag() == null)
                super.setTag("");
        }

    }


    /*
        Construct view from this layout
     */
    public abstract int getLayoutResId();

}
