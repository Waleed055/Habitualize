package com.example.habitualize.utils

import android.content.res.Resources
import android.os.Build
import com.example.habitualize.R


class MyTheme(original: Resources) :
    Resources(original.assets, original.displayMetrics, original.configuration) {

    @Throws(NotFoundException::class)
    override fun getColor(id: Int): Int {
        return getColor(id, null)
    }

    @Throws(NotFoundException::class)
    override fun getColor(id: Int, theme: Theme?): Int {
        println("checking the theme code 1  ${getResourceEntryName(id)}")
        return when (getResourceEntryName(id)) {
            "theme" -> { // You can change the return value to an instance field that loads from SharedPreferences.
                println("checking the theme code 2")
                R.color.green// used as an example. Change as needed.
            }
            else -> {
                println("checking the theme code 3")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    println("checking the theme code 4")
                    super.getColor(id, theme)
                } else {
                    println("checking theme the code 5")
                    super.getColor(id)
                }
            }
        }
    }

}