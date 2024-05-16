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

class PasswordEditText : AppCompatEditText {
    private lateinit var parentLayout: TextInputLayout
    private var mainPasswordEditText: PasswordEditText? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = ContextCompat.getString(context, R.string.hintpassword)
        background = ContextCompat.getDrawable(context, R.drawable.custom_text_input)
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    parentLayout.error = "Password must at least have 8 characters"
                } else {
                    parentLayout.error = null
                }

                mainPasswordEditText?.let {
                    if (s.toString() != it.text.toString()) {
                        parentLayout.error = "Password doesn't match"
                    } else {
                        parentLayout.error = null
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun setParentLayout(layout: TextInputLayout) {
        parentLayout = layout
    }

    fun setMainPasswordEditText(mainPasswordEditText: PasswordEditText) {
        this.mainPasswordEditText = mainPasswordEditText
    }
}