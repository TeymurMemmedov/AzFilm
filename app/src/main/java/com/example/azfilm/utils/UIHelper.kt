package com.example.azfilm.utils

import android.text.InputType
import android.widget.EditText
import android.widget.ImageButton
import com.example.azfilm.R

class UIHelper {

    companion object
    {
        fun hideShowPassword(ev: EditText, btn: ImageButton) {
            val currentType = ev.inputType
            val typ = ev.typeface

            println("font_type$typ")

            val newType = when (currentType) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD -> {
                    btn.setImageResource(R.drawable.icon_visibility_off)
                    InputType.TYPE_CLASS_TEXT
                }

                InputType.TYPE_CLASS_TEXT -> {
                    btn.setImageResource(R.drawable.icon_visibility)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }

                else -> currentType
            }

            ev.inputType = newType
            println("font_type$typ")

            //Normal text input->monospace
            //Password text input->Sans Serif

            //Cevrilmeler zamani ölçü dəyişir deyə bu əməliyyat edilir.


            val font = if (newType == InputType.TYPE_CLASS_TEXT) {
                // Use a fixed-width font for normal text input
                android.graphics.Typeface.DEFAULT
            } else {
                // Use the default font for password input
                android.graphics.Typeface.MONOSPACE
            }
            ev.typeface = android.graphics.Typeface.create(font, android.graphics.Typeface.NORMAL)



        }
    }
}