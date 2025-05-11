package com.zulfa.readwish.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.zulfa.readwish.R
import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.ui.BookAdapter
import com.zulfa.readwish.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()
    private val bookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupSearchListener()
    }

    private fun setupRecyclerView() {
        binding.rvFavoriteBooks.apply {
            adapter = bookAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupSearchListener() {
        val searchEditText = binding.searchField

        // Listener for "Enter" or "Search" key press
        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                val query = v.text.toString().trim()
                bookAdapter.submitList(emptyList())
                if (query.isNotEmpty()) {
                    when (binding.toggleButton.checkedButtonId) {
                        R.id.bt_by_author_title -> observeSearchBooks(query)
                        R.id.bt_by_topic -> observeSearchBooksByTopic(query)
                    }
                }
                // Return true to indicate that the event was handled
                return@setOnEditorActionListener true
            }
            false
        }
        binding.toggleButton.addOnButtonCheckedListener { _, _, isChecked ->
            if (isChecked) {
                val query = searchEditText.text.toString().trim()
                bookAdapter.submitList(emptyList())
                if (query.isNotEmpty()) {
                    when (binding.toggleButton.checkedButtonId) {
                        R.id.bt_by_author_title -> observeSearchBooks(query)
                        R.id.bt_by_topic -> observeSearchBooksByTopic(query)
                    }
                }
            }
        }
    }

    private fun observeSearchBooks(query: String) {
        viewModel.searchBooks(query).observe(viewLifecycleOwner) { result ->
            handleSearchResult(result)
        }
    }

    private fun observeSearchBooksByTopic(query: String) {
        viewModel.searchBooksByTopic(query).observe(viewLifecycleOwner) { result ->
            handleSearchResult(result)
        }
    }

    private fun handleSearchResult(result: Resource<List<Book>>) {
        when (result) {
            is Resource.Loading -> {
                binding.loadingBar.visibility = View.VISIBLE
            }
            is Resource.Success -> {
                binding.loadingBar.visibility = View.GONE
                bookAdapter.submitList(result.data)
            }
            is Resource.Error -> {
                binding.loadingBar.visibility = View.GONE
                Toast.makeText(requireContext(), result.message ?: "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}