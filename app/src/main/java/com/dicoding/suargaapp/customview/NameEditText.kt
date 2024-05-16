package com.dicoding.suargaapp.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R
import com.google.android.material.textfield.TextInputLayout

class NameEditText : AppCompatEditText {
    private lateinit var parentLayout: TextInputLayout

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = ContextCompat.getString(context, R.string.name_hint)
        background = ContextCompat.getDrawable(context, R.drawable.custom_text_input)
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                parentLayout.error = null
                if (s.isNullOrEmpty()) {
                    parentLayout.error = "Name cannot be empty!"
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    fun setParentLayout(layout: TextInputLayout) {
        parentLayout = layout
    }
}