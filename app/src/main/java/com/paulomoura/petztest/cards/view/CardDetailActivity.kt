package com.paulomoura.petztest.cards.view

import android.annotation.SuppressLint
import android.os.Build.VERSION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.model.dto.Card
import com.paulomoura.petztest.databinding.ActivityCardDetailBinding
import com.paulomoura.petztest.extensions.bindings
import com.paulomoura.petztest.extensions.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardDetailActivity : AppCompatActivity() {

    private val binding by bindings(ActivityCardDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(R.string.card_details_toolbar_title)
        loadCardDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun loadCardDetails() {
        val card = if (VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(CARD_EXTRA, Card::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(CARD_EXTRA)
        }
        card?.let {
            with(binding) {
                textName.text = it.name
                textFlavor.text = it.flavor
                textShortText.text = it.text
                textSet.text = "Set: ${it.cardSet}"
                textType.text = "Text: ${it.type}"
                textFaction.text = "Faction: ${it.faction}"
                textRarity.text = "Rarity: ${it.rarity}"
                textAttack.text = "Attack: ${it.attack}"
                textCost.text = "Cost: ${it.cost}"
                textHealth.text = "Health: ${it.health}"

                Glide.with(this@CardDetailActivity)
                    .load(it.img)
                    .into(cardImage)
            }
        }
    }

    companion object {
        const val CARD_EXTRA = "CARD_EXTRA"
    }
}