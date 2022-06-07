package com.martini.dartplayer.data.local.musicManager

class IsProbablyAudio {
    operator fun invoke(
        displayName: String
    ): Boolean {
        return !displayName.contains("AUD-") && !displayName.contains("-WAV")
    }
}