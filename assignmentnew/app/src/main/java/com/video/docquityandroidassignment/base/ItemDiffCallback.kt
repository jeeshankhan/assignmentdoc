package com.video.docquityandroidassignment.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class ItemDiffCallback<S> : DiffUtil.ItemCallback<S>()  {




    override fun areItemsTheSame(oldItem: S & Any, newItem: S & Any): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: S & Any, newItem: S & Any): Boolean {
        return oldItem == newItem
    }
}