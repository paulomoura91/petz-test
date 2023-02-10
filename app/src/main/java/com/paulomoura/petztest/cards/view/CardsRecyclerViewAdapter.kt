package com.paulomoura.petztest.cards.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulomoura.petztest.cards.model.dto.Card
import com.paulomoura.petztest.databinding.ItemlistCardsBinding

class CardsRecyclerViewAdapter(
    private val cards: List<Card>,
    private val clickListener: (Card) -> Unit
) : RecyclerView.Adapter<CardsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemlistCardsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.nameView.text = card.name
        holder.flavorView.text = card.flavor
        holder.typeView.text = card.type
        holder.root.setOnClickListener {
            clickListener.invoke(card)
        }
    }

    override fun getItemCount(): Int = cards.size

    inner class ViewHolder(binding: ItemlistCardsBinding) : RecyclerView.ViewHolder(binding.root) {
        val root = binding.root
        val nameView = binding.name
        val flavorView = binding.flavor
        val typeView = binding.type
    }

}