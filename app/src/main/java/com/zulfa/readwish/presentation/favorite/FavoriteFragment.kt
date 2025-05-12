package com.zulfa.readwish.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zulfa.readwish.R
import com.zulfa.readwish.core.ui.BookAdapter
import com.zulfa.readwish.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private val bookAdapter = BookAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity != null){

            bookAdapter.onItemClick ={ selectedData ->
                val bundle = Bundle().apply {
                    putParcelable("EXTRA_DATA", selectedData)
                }

                findNavController().navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
            }

            favoriteViewModel.favoriteBook.observe(viewLifecycleOwner){dataBook ->
                bookAdapter.submitList(dataBook)
            }
            with(binding.rvFavoriteBooks){
                layoutManager = GridLayoutManager(context,2)
                setHasFixedSize(true)
                adapter = bookAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}