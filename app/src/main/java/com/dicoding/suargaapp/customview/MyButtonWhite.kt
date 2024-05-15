package com.dicoding.suargaapp.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R

class MyButtonWhite : AppCompatButton {
    constructor(context: Context) : super(context) // untuk di Activity/Fragment
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)  // untuk di XML

    private var txtColor: Int = 0
    private var enabledBackground: Drawable

    init {
        txtColor = ContextCompat.getColor(context, R.color.primary70)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_white) as Drawable
    }

    @SuppressLint("SetTextI18n")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
        text = "Lewati"
    }
}