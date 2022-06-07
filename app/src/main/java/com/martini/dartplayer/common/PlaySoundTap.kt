package com.martini.dartplayer.common

import android.content.Context
import android.media.AudioManager

fun playSoundOnTap(context: Context) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
}