package com.paulomoura.petztest.cards.view

import android.os.Build.VERSION
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        setContentView(R.layout.activity_card_detail)
        setupToolbar(R.string.card_details_toolbar_title)
        loadCardDetails()
    }

    private fun loadCardDetails() {
        val card = if (VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(CARD_EXTRA, Card::class.java)
        } else {
            @Suppress("DEPRECATION") intent.getParcelableExtra(CARD_EXTRA)
        }
        card?.let {
            Toast.makeText(this, card.name, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val CARD_EXTRA = "CARD_EXTRA"
    }
}