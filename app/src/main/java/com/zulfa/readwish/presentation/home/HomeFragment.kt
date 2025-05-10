package com.zulfa.readwish.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.core.ui.BookAdapter
import com.zulfa.readwish.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    private val topBookAdapter = BookAdapter()
    private val latestBookAdapter = BookAdapter()
    private val oldestBookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            setupRecyclerView(binding.rvTopBooks, topBookAdapter)
            setupRecyclerView(binding.rvLatestBooks, latestBookAdapter)
            setupRecyclerView(binding.rvOldestBooks, oldestBookAdapter)

            observeBooks(homeViewModel.defaultBooks, topBookAdapter, binding.topLoadingBar)
            observeBooks(homeViewModel.descendingBooks, latestBookAdapter, binding.latestLoadingBar)
            observeBooks(homeViewModel.ascendingBooks, oldestBookAdapter, binding.oldestLoadingBar)

            val itemClickListener: (Book) -> Unit = { selectedData ->
                Toast.makeText(context, "Details of $selectedData", Toast.LENGTH_SHORT).show()
            }

            topBookAdapter.onItemClick = itemClickListener
            latestBookAdapter.onItemClick = itemClickListener
            oldestBookAdapter.onItemClick = itemClickListener

            setupChipClickListeners()
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: BookAdapter) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    private fun observeBooks(
        liveData: LiveData<Resource<List<Book>>>,
        adapter: BookAdapter,
        loadingBar: View
    ) {
        liveData.observe(viewLifecycleOwner) { bookResource ->
            when (bookResource) {
                is Resource.Loading -> {
                    loadingBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    loadingBar.visibility = View.GONE
                    adapter.submitList(bookResource.data)
                }
                is Resource.Error -> {
                    loadingBar.visibility = View.GONE
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupChipClickListeners() {
        binding.chipAll.setOnClickListener { homeViewModel.selectTopic("all") }
        binding.chipFiction.setOnClickListener { homeViewModel.selectTopic("fiction") }
        binding.chipSciFi.setOnClickListener { homeViewModel.selectTopic("science fiction") }
        binding.chipChildren.setOnClickListener { homeViewModel.selectTopic("children") }
        binding.chipHistory.setOnClickListener { homeViewModel.selectTopic("history") }
        binding.chipScience.setOnClickListener { homeViewModel.selectTopic("science") }
        binding.chipBiography.setOnClickListener { homeViewModel.selectTopic("biography") }
        binding.chipPhilosophy.setOnClickListener { homeViewModel.selectTopic("philosophy") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}