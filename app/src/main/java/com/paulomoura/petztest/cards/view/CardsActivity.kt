package com.paulomoura.petztest.cards.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paulomoura.petztest.R
import com.paulomoura.petztest.extensions.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards)
        setupToolbar(R.string.cards_toolbar_title)
        showCardSetsList()
    }

    private fun showCardSetsList() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, CardSetsFragment.newInstance())
        transaction.commit()
    }
}