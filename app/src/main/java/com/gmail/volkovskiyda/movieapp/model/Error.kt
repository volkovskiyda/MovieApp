package com.gmail.volkovskiyda.movieapp.model

import android.content.Context
import androidx.annotation.StringRes

sealed class Error {
    data class Text(val text: String) : Error()
    data class Resource(@StringRes val resId: Int) : Error()

    fun toText(context: Context): String = when (this) {
        is Text -> text
        is Resource -> context.getString(resId)
    }
}