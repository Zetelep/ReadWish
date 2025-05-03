package com.zulfa.readwish.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zulfa.readwish.core.domain.model.Book
import com.zulfa.readwish.databinding.BookItemBinding

class BookAdapter:  ListAdapter<Book, BookAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Book) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            BookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }


    inner class ListViewHolder(private var binding: BookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Book) {
            Glide.with(itemView.context)
                .load(data.coverBook)
                .into(binding.itemImage)
            binding.itemTitle.text = data.title
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Book> =
            object : DiffUtil.ItemCallback<Book>() {
                override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                    return oldItem == newItem
                }
            }
    }
}