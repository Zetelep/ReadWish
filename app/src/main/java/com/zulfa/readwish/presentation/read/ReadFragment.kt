package com.zulfa.readwish.presentation.read

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgument
import com.zulfa.readwish.R
import com.zulfa.readwish.databinding.FragmentListMoreBinding
import com.zulfa.readwish.databinding.FragmentReadBinding
import com.zulfa.readwish.presentation.list.ListMoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReadFragment : Fragment() {
    private var _binding: FragmentReadBinding? =null
    private val binding get() = _binding!!
    private val readViewModel: ReadViewModel by viewModel()

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
            Log.e("Link got","Link = $link")
            link?.let {
                with(binding.bookWebView) {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    loadUrl(link)
                }
            }
            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}