package com.example.acplite.ui.fragments

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.app.DatePickerDialog
import android.app.Dialog
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.acplite.ui.fragments.DatePickerFragment
import java.util.*

class DatePickerFragment : DialogFragment(), OnDateSetListener {
    private var listener: OnDateSetListener? = null
    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c[Calendar.YEAR]
        val month = c[Calendar.MONTH]
        val day = c[Calendar.DAY_OF_MONTH]

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(activity!!, listener, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
    }

    companion object {
        @JvmStatic
        fun newInstance(listener: OnDateSetListener?): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.setListener(listener)
            return fragment
        }
    }
}