package com.bikcodeh.mubi.data.repository

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Assert.assertEquals
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.bikcodeh.parking", appContext.packageName)
    }
}