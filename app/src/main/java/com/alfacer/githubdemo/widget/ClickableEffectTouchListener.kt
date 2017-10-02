package com.alfacer.githubdemo.widget

import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout

/**
 * Created by albert on 10/1/17.
 */

class ClickableEffectTouchListener : View.OnTouchListener {
    internal var mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP
    private var onClickListener: View.OnClickListener? = null
    private var filtercolor = 0x77000000

    constructor(onClickListener: View.OnClickListener) {
        this.onClickListener = onClickListener
    }

    constructor(onClickListener: View.OnClickListener, filtercolor: Int) {
        this.onClickListener = onClickListener
        this.filtercolor = filtercolor
    }

    constructor(onClickListener: View.OnClickListener, mode: PorterDuff.Mode) {

        this.onClickListener = onClickListener
        this.mode = mode
    }


    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                try {
                    if (v is ImageButton)
                        v.getBackground().setColorFilter(filtercolor, mode)
                    else if (v is ImageView)
                        v.drawable.setColorFilter(filtercolor, mode)
                    else if (v is RelativeLayout) {
                        if (v.getBackground() != null)
                            v.getBackground().setColorFilter(filtercolor, mode)
                    } else if (v is FrameLayout) {
                        if (v.getBackground() != null)
                            v.getBackground().setColorFilter(filtercolor, mode)
                    }
                } catch (e: Exception) {

                }

                v.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                if (onClickListener != null) {
                    onClickListener!!.onClick(v)
                }
                run {
                    try {
                        if (v is ImageButton)
                            v.getBackground().clearColorFilter()
                        else if (v is ImageView)
                            v.drawable.clearColorFilter()
                        else if (v is RelativeLayout) {
                            if (v.getBackground() != null)
                                v.getBackground().clearColorFilter()
                        } else if (v is FrameLayout) {
                            if (v.getBackground() != null)
                                v.getBackground().clearColorFilter()
                        }
                    } catch (e: Exception) {

                    }
                    v.invalidate()
                }
            }

            MotionEvent.ACTION_CANCEL -> {
                try {
                    if (v is ImageButton)
                        v.getBackground().clearColorFilter()
                    else if (v is ImageView)
                        v.drawable.clearColorFilter()
                    else if (v is RelativeLayout) {
                        if (v.getBackground() != null)
                            v.getBackground().clearColorFilter()
                    } else if (v is FrameLayout) {
                        if (v.getBackground() != null)
                            v.getBackground().clearColorFilter()
                    }
                } catch (e: Exception) {
                }

                v.invalidate()
            }
        }
        return true
    }
}
