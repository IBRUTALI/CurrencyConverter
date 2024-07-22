package com.ighorosipov.currencyconverter.utils.base

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import java.util.regex.Pattern

fun MaterialToolbar.setTitle(label: CharSequence?, textView: TextView, arguments: Bundle?) {
    if (label != null) {
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments.get(argName).toString())
            } else {
                return
            }
        }
        matcher.appendTail(title)
        setTitle("")
        textView.text = title
    }
}

fun Fragment.showToast(
    message: String,
    longLength: Boolean = false
) {
    Toast.makeText(
        requireContext(),
        message,
        if (longLength) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    ).show()
}