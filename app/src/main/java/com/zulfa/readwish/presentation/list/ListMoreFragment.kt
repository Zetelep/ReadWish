package com.zulfa.readwish.presentation.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zulfa.readwish.R
import com.zulfa.readwish.core.data.Resource
import com.zulfa.readwish.core.ui.BookAdapter
import com.zulfa.readwish.databinding.FragmentDetailBinding
import com.zulfa.readwish.databinding.FragmentListMoreBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ListMoreFragment : Fragment() {

    private var _binding: FragmentListMoreBinding? =null
    private val binding get() = _binding!!
    private val listMoreViewModel: ListMoreViewModel by viewModel {
        parametersOf(
            arguments?.getString("topic") ?: "all",
            arguments?.getString("sort") ?: ""
        )
    }

    private val bookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            bookAdapter.onItemClick = {selectedData ->
                Toast.makeText(context, "Details of $selectedData", Toast.LENGTH_SHORT).show()
                val bundle = Bundle().apply {
                    putParcelable("EXTRA_DATA", selectedData)
                }
                findNavController().navigate(R.id.action_listMoreFragment_to_detailFragment, bundle)

            }

            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            listMoreViewModel.books.observe(viewLifecycleOwner){book ->
                if(book != null){
                    when(book){
                        is Resource.Error -> {
                            binding.loadingBar.visibility = View.GONE
                            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Resource.Loading -> binding.loadingBar.visibility = View.VISIBLE
                        is Resource.Success ->{
                            binding.loadingBar.visibility = View.GONE
                            bookAdapter.submitList(book.data)
                        }
                    }
                }
            }
            with(binding.rvMoreBooks) {
                layoutManager = GridLayoutManager(context,2)
                setHasFixedSize(true)
                adapter = bookAdapter
            }

        }
    }

}