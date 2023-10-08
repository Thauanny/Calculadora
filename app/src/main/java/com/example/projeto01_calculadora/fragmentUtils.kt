package com.example.projeto01_calculadora

import android.view.View
import android.view.inputmethod.InputMethodManager

object FragmentUtils {
    public fun hideKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(InputMethodManager::class.java);
        inputMethodManager?.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        );
    }
}