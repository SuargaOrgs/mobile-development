package com.dicoding.suargaapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.Spinner
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R

class DropdownOptionView : FrameLayout {

    private lateinit var dropdownSpinner: Spinner

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.view_dropdown_option, this)

        dropdownSpinner = findViewById(R.id.dropdownSpinner)
        dropdownSpinner.gravity = Gravity.CENTER

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropdownOptionView)
        val optionsResId = typedArray.getResourceId(R.styleable.DropdownOptionView_options, 0)
        if (optionsResId != 0) {
            val options = resources.getStringArray(optionsResId)
            setOptions(options)
        }
        typedArray.recycle()

        dropdownSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedOption = parent.getItemAtPosition(position) as String
                // Handle selection if needed
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        background = ContextCompat.getDrawable(context, R.drawable.custom_option_layout)
    }

    fun setOptions(options: Array<String>) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, options)
        dropdownSpinner.adapter = adapter
    }

    fun getSelectedOption(): String {
        return dropdownSpinner.selectedItem as String
    }
}
