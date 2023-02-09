package com.paulomoura.petztest.cards.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paulomoura.petztest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}