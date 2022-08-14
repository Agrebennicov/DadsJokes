package com.agrebennicov.jetpackdemo.common.util

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.agrebennicov.jetpackdemo.R
import com.agrebennicov.jetpackdemo.common.pojo.Joke
import javax.inject.Inject

class ShareUtil @Inject constructor() {
    fun share(context: Context, joke: Joke) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = SHARE_TYPE
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(
            Intent.EXTRA_TEXT,
            context.getString(R.string.sharing_content, joke.content, APP_LINK)
        )
        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, context.getString(R.string.intent_chooser_title)),
            null
        )
    }

    fun share(context: Context, jokes: List<Joke>) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = SHARE_TYPE
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val content = StringBuilder("")
        content.append(context.resources.getString(R.string.sharing_title))
        content.appendLine()
        content.appendLine()
        jokes.forEachIndexed { index, joke ->
            if (index != 0) content.appendLine().appendLine()
            content.append(context.getString(R.string.sharing_joke_number, (index + 1)))
            content.appendLine()
            content.append(joke.content)
            if (index == jokes.lastIndex) content.append(
                context.getString(R.string.sharing_joke_app_link, APP_LINK)
            )
        }
        intent.putExtra(Intent.EXTRA_TEXT, content.toString())
        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, context.getString(R.string.intent_chooser_title)),
            null
        )
    }

    companion object {
        private const val SHARE_TYPE = "text/plain"
        private const val APP_LINK =
            "https://play.google.com/store/apps/details?id=com.agrebennicov.jetpackdemo"
    }
}
