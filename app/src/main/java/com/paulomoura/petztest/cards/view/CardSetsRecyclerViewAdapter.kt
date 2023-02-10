package com.paulomoura.petztest.cards.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulomoura.petztest.databinding.ItemlistCardSetsBinding

class CardSetsRecyclerViewAdapter(
    private val cardSets: List<String>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<CardSetsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemlistCardSetsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardSet = cardSets[position]
        holder.cardsetName.text = cardSet
        holder.root.setOnClickListener {
            clickListener.invoke(cardSet)
        }
    }

    override fun getItemCount(): Int = cardSets.size

    inner class ViewHolder(binding: ItemlistCardSetsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val cardsetName = binding.cardsetName
    }
}