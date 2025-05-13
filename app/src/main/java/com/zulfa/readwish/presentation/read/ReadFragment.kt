package com.zulfa.readwish.presentation.read

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.zulfa.readwish.databinding.FragmentReadBinding

class ReadFragment : Fragment() {
    private var _binding: FragmentReadBinding? =null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReadBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val link = arguments?.getString("link")
            val title = arguments?.getString("title")
            Log.e("Link got","Link = $link")
            link?.let {
                with(binding.bookWebView) {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    loadUrl(link)
                }
            }

            binding.topAppBar.title = title
            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}