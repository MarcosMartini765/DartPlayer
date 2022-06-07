package com.martini.dartplayer.data.local.musicManager

import android.content.Intent
import android.net.Uri
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.martini.dartplayer.common.InvalidUriException
import com.martini.dartplayer.common.NothingToDoException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.NumberFormatException

@RunWith(AndroidJUnit4::class)
class MusicOpenIntentParserTest {
    private val musicOpenIntentParser = MusicOpenIntentParser()

    @Test(expected = NothingToDoException::class)
    fun shouldThrowExceptionNothingToDo() {
        val testIntent = Intent("android.intent.action.SOMETHING")
        musicOpenIntentParser(testIntent)
    }

    @Test(expected = InvalidUriException::class)
    fun shouldThrowExceptionInvalidUri() {
        val testIntent = Intent("android.intent.action.VIEW").also {
            it.data = null
        }
        musicOpenIntentParser(testIntent)
    }

    @Test
    fun shouldTReturnLong() {
        val number = 486L

        val testIntent = Intent().also {
            it.data = Uri.parse("something/$number")
            it.action = "android.intent.action.VIEW"
        }
        val result = musicOpenIntentParser(testIntent)

        Assert.assertEquals(result, number)
    }

    @Test(expected = NumberFormatException::class)
    fun shouldThrowException() {
        val testIntent = Intent().also {
            it.data = Uri.parse("something/dsad")
            it.action = "android.intent.action.VIEW"
        }
        musicOpenIntentParser(testIntent)
    }
}