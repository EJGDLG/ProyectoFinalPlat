package systems.altimit.rpgmakermv

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Message
import android.util.AttributeSet
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebPlayerView : WebView {

    private var mPlayer: Player

    constructor(context: Context) : super(context) {
        mPlayer = WebPlayer(this)
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mPlayer = WebPlayer(this)
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPlayer = WebPlayer(this)
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        mPlayer = WebPlayer(this)
        init(context)
    }

    private fun init(context: Context) {
        mPlayer = WebPlayer(this)

        setBackgroundColor(Color.BLACK)

        enableJavascript()

        val webSettings: WebSettings = settings
        webSettings.allowContentAccess = true
        webSettings.allowFileAccess = true

        webSettings.databaseEnabled = true
        webSettings.databasePath = context.getDir("database", Context.MODE_PRIVATE).path
        webSettings.domStorageEnabled = true
        webSettings.loadsImagesAutomatically = true
        webSettings.setSupportMultipleWindows(true)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webSettings.allowFileAccessFromFileURLs = true
            webSettings.allowUniversalAccessFromFileURLs = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                webSettings.mediaPlaybackRequiresUserGesture = false
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        }

        webChromeClient = ChromeClient()
        webViewClient = ViewClient()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun enableJavascript() {
        val webSettings: WebSettings = settings
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.javaScriptEnabled = true
    }

    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean {
        return false
    }

    override fun scrollTo(x: Int, y: Int) {}

    override fun computeScroll() {}

    fun getPlayer(): Player {
        return mPlayer
    }

    private inner class ChromeClient : WebChromeClient() {

        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
            if ("Scripts may close only the windows that were opened by it." == consoleMessage.message()) {
                if (mPlayer.getContext() is WebPlayerActivity) {
                    (mPlayer.getContext() as WebPlayerActivity).finish()
                }
            }
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            val dumbWV = WebView(view.context)
            dumbWV.setWebViewClient(object : WebViewClient() {
                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    view.context.startActivity(browserIntent)
                }
            })
            (resultMsg.obj as WebView.WebViewTransport).webView = dumbWV
            resultMsg.sendToTarget()
            return true
        }
    }

    private inner class ViewClient : WebViewClient() {

        @TargetApi(Build.VERSION_CODES.M)
        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            view.setBackgroundColor(Color.WHITE)
        }

        @SuppressWarnings("deprecation")
        override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            view.setBackgroundColor(Color.WHITE)
        }
    }

    private inner class WebPlayer(private val mWebView: WebPlayerView) : Player {

        override fun setKeepScreenOn() {
            mWebView.setKeepScreenOn(true)
        }

        override fun getView(): View {
            return mWebView
        }

        override fun loadUrl(url: String) {
            mWebView.loadUrl(url)
        }

        @SuppressLint("JavascriptInterface")
        override fun addJavascriptInterface(`object`: Any, name: String) {
            mWebView.addJavascriptInterface(`object`, name)
        }

        override fun getContext(): Context {
            return mWebView.context
        }

        override fun loadData(data: String) {
            mWebView.loadData(data, "text/html", "base64")
        }

        override fun evaluateJavascript(script: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebView.evaluateJavascript(script, null)
            } else {
                mWebView.loadUrl("javascript:$script")
            }
        }

        override fun post(runnable: Runnable) {
            mWebView.post(runnable)
        }

        override fun removeJavascriptInterface(name: String) {
            mWebView.removeJavascriptInterface(name)
        }

        override fun pauseTimers() {
            mWebView.pauseTimers()
        }

        override fun onHide() {
            mWebView.onPause()
        }

        override fun resumeTimers() {
            mWebView.resumeTimers()
        }

        override fun onShow() {
            mWebView.onResume()
        }

        override fun onDestroy() {
            mWebView.destroy()
        }
    }
}