package com.martini.dartplayer.data.local.musicManager

import android.content.Intent
import com.martini.dartplayer.common.InvalidUriException
import com.martini.dartplayer.common.NothingToDoException

class MusicOpenIntentParser {
    operator fun invoke(intent: Intent): Long {
        if (intent.action != "android.intent.action.VIEW") throw NothingToDoException()

        val songPath = intent.dataString ?: throw InvalidUriException()

        return songPath.filter { it.isDigit() }.toLong()
    }
}