package com.bikcodeh.mubi.presentation.util

object Util {
    fun formatRunTime(time: Int?): String {
        if (time == null) return ""
        val hours = time / 60
        val minutes = time % 60
        val builder =
            StringBuilder().append(hours).append("h").append(" ").append(minutes).append("m")
        return builder.toString()
    }
}