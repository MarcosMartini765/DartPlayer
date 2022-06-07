package com.martini.dartplayer.data.local.musicManager

import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class IsProbablyAudioTest {

    private val isProbablyAudio = IsProbablyAudio()

    @Test
    fun shouldReturnFalse() = runBlocking {
        val displayName = "-WAVdkaodsdAUD-"

        val isAudio = isProbablyAudio(displayName)

        Assert.assertFalse(isAudio)
    }

    @Test
    fun shouldReturnTrue() = runBlocking {
        val displayName = "Flavour of life.m4a"

        val isAudio = isProbablyAudio(displayName)

        Assert.assertTrue(isAudio)
    }
}