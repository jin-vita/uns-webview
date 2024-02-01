package org.techtown.unswebview

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import org.techtown.unswebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tag: String = javaClass.simpleName

    private val dialog: Dialog by lazy { AppData.showLoadingDialog(this) }

    override fun onBackPressed() {
        binding.connectLayout.visibility = View.VISIBLE
        binding.webView.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        val method: String = Thread.currentThread().stackTrace[2].methodName
        AppData.debug(tag, "$method called")

        connectButton.setOnClickListener { showWebView() }
    }

    private fun ActivityMainBinding.showWebView() {
        val method: String = Thread.currentThread().stackTrace[2].methodName
        AppData.debug(tag, "$method called")
        dialog.show()

        if (ipInput.text.toString().toIntOrNull() == null) {
            dialog.hide()
            AppData.showToast(this@MainActivity, "숫자만 입력 필요")
            return
        }

        if (ipInput.text.trim().length != 6) {
            dialog.hide()
            AppData.showToast(this@MainActivity, "6자리 입력 필요")
            return
        }

        val inputIp = StringBuilder(ipInput.text.trim()).insert(3, ".").toString()

        webView.webViewClient = MyWebViewClient()

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl("http://192.168.$inputIp:8080/map_viewer_mobile")
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            dialog.hide()
            binding.connectLayout.visibility = View.GONE
            binding.webView.visibility = View.VISIBLE
            Utils.applyFullScreen(supportActionBar, window)
            AppData.showToast(this@MainActivity, "로딩 완료")
        }
    }
}