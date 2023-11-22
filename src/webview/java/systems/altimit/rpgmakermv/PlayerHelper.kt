package systems.altimit.rpgmakermv

import android.content.Context
import android.webkit.JavascriptInterface

object PlayerHelper {

    fun create(context: Context): Player {
        return WebPlayerView(context).player
    }

    abstract class Interface {
        protected abstract fun onStart()
        protected abstract fun onPrepare(webgl: Boolean, webaudio: Boolean, showfps: Boolean)

        @JavascriptInterface
        fun start() {
            onStart()
        }

        @JavascriptInterface
        fun prepare(webgl: Boolean, webaudio: Boolean, showfps: Boolean) {
            onPrepare(webgl, webaudio, showfps)
        }
    }
}

