package com.zulfa.readwish.presentation.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.zulfa.readwish.R
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.databinding.FragmentDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? =null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelable("EXTRA_DATA", Book::class.java)
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelable<Book>("EXTRA_DATA")
            }

            book?.let { selectedBook ->
                bindBookData(selectedBook)
                detailViewModel.saveBookToLocal(selectedBook)
            }

            binding.topAppBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

    }

    private fun bindBookData(book: Book) {
        binding.tvBookTitle.text = book.title
        binding.tvAuthorName.text = if(book.authors.size == 1 ) {
            book.authors.firstOrNull()
        }else {
            buildString {
                append(book.authors[1])
                append(" ")
                append(book.authors[0])
            }
        }
        binding.tvSummary.text = book.summaries.joinToString(", ")
        binding.favoriteButton.isChecked = book.isFavorite

        // Set book cover if using URL (use Glide or Coil)
        Glide.with(this)
            .load(book.coverBook)
            .placeholder(R.drawable.sample_cover)
            .into(binding.ivBookCover)

        // Populate genre chip group
        binding.genreChipGroup.removeAllViews()
        book.subject.forEach { genre ->
            val chip = Chip(requireContext())
            chip.text = genre
            chip.isClickable = false
            chip.isCheckable = false
            binding.genreChipGroup.addView(chip)
        }

        // Favorite toggle
        binding.favoriteButton.setOnCheckedChangeListener { _, isChecked ->
            detailViewModel.setFavoriteBook(book, isChecked)
        }

        // Action button
        binding.actionButton.setOnClickListener {
            Toast.makeText(requireContext(), "Opening '${book.title}'...", Toast.LENGTH_SHORT).show()
            val bundle = Bundle().apply {
                putString("link", book.htmlBook)
                putString("title",book.title)
            }
            findNavController().navigate(R.id.action_detailFragment_to_readFragment, bundle)        }
    }

}