package com.example.calculator.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast

class ToastManager {
    companion object {
        private var toast: Toast? = null

        fun showToast(context: Context, layoutId: Int) {
            toast?.cancel()

            val inflater = LayoutInflater.from(context)
            val toastLayout = inflater.inflate(layoutId, null)

            toast = Toast(context).apply {
                duration = Toast.LENGTH_SHORT
                setGravity(Gravity.BOTTOM, 0, 50)
                view = toastLayout
            }
            toast?.show()
        }
    }
}
