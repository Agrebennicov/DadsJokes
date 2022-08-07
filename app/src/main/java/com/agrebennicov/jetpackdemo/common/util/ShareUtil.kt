package com.agrebennicov.jetpackdemo.common.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import javax.inject.Inject

class ShareUtil @Inject constructor() {
    fun share(context: Context, joke: Joke) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "${joke.content}\n\nCheck out more jokes from dad\nhttps://www.youtube.com/"
        )
        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, "Share With:"),
            null
        )
    }
}
