package com.smedic.hr

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.web_page_fragment.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class WebPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.web_page_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actorBiographyUrl = arguments?.get(ACTOR_BIOGRAPHY_URL) as String
        loadWebPage(actorBiographyUrl)

        close_button.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebPage(url: String) {
        web_view.settings.javaScriptEnabled = true
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress_bar != null && progress == 100) {
                    progress_bar.visibility = View.GONE
                }
            }
        }
        web_view.loadUrl(url)
    }

    companion object {
        const val ACTOR_BIOGRAPHY_URL = "actor_biography_url"
    }
}