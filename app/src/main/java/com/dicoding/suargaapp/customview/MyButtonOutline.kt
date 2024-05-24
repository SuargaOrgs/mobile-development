package com.dicoding.suargaapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R

class MyButtonOutline : AppCompatButton {

    private var txtColor: Int = 0
    private lateinit var enabledBackground: Drawable
    private var startIcon: Drawable? = null
    private var endIcon: Drawable? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        txtColor = ContextCompat.getColor(context, android.R.color.background_dark)
        enabledBackground = ContextCompat.getDrawable(context, R.drawable.custom_option_layout) as Drawable
        startIcon = ContextCompat.getDrawable(context, R.drawable.ic_formula)
        endIcon = ContextCompat.getDrawable(context, R.drawable.ic_open)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = enabledBackground
        setTextColor(txtColor)
        textSize = 12f

        setCompoundDrawablesWithIntrinsicBounds(startIcon, null, endIcon, null)

        compoundDrawablePadding = 8
    }
}
