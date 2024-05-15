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

class MyButton : AppCompatButton {

    constructor(context: Context) : super(context) // untuk di Activity/Fragment
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)  // untuk di XML

    private var txtColor: Int = 0
    private var enabledBackground: Drawable

    init {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.bg_button) as Drawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColor)
        textSize = 12f
        gravity = Gravity.CENTER
    }
}