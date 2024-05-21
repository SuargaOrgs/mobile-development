package com.dicoding.suargaapp.customview

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.DatePicker
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.suargaapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CalendarEditText : AppCompatEditText, DatePickerDialog.OnDateSetListener {
    private val calendar: Calendar = Calendar.getInstance()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        isFocusable = false // Membuat EditText tidak bisa di-edit secara langsung
        isClickable = true  // Membuat EditText bisa diklik untuk memunculkan dialog
        setOnClickListener {
            showDatePickerDialog()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.getString(R.string.date_hint) // Mengatur hint untuk tanggal
        background = ContextCompat.getDrawable(context, R.drawable.custom_text_input) // Mengatur background custom
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(
            context,
            R.style.CustomDatePickerDialog, // Menggunakan tema kustom
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

    private fun updateLabel() {
        val format = "yyyy-MM-dd" // Format tanggal
        val sdf = SimpleDateFormat(format, Locale.US)
        setText(sdf.format(calendar.time))
    }
}
