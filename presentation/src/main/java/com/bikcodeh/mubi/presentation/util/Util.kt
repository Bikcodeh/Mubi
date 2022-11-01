package com.bikcodeh.mubi.presentation.util

object Util {
    fun formatRunTime(time: Int?): String {
        if (time == null) return ""
        val hours = time / 60
        val minutes = time % 60
        val builder = StringBuilder()
        if (hours != 0) {
            builder.append(hours).append("h").append(" ")
        }
        builder.append(minutes).append("m")
        return builder.toString()
    }
}