package com.bikcodeh.mubi.presentation.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UtilTest {

    @Test
    fun `formatRunTime should return only minutes`() {
        val result = Util.formatRunTime(40)
        assertThat(result).isEqualTo("40m")
    }

    @Test
    fun `formatRunTime should return only hour`() {
        val result = Util.formatRunTime(60)
        assertThat(result).isEqualTo("1h 0m")
    }

    @Test
    fun `formatRunTime should return hour and minutes`() {
        val result = Util.formatRunTime(75)
        assertThat(result).isEqualTo("1h 15m")
    }

    @Test
    fun `formatRunTime should return an empty string with a null value`() {
        val result = Util.formatRunTime(null)
        assertThat(result).isEqualTo("")
    }
}