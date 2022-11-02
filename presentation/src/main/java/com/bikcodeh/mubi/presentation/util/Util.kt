package com.bikcodeh.mubi.presentation.util

object Util {
    /**
     * Function that receives a time and returns it in hours with minutes or only minutes
     * @param time: time in minutes  for example 75 -> 1h 15m
     * @return String: time in string format
     */
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