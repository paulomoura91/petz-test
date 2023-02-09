package com.paulomoura.petztest.extensions

import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.paulomoura.petztest.R

@MainThread
inline fun <T: ViewBinding> AppCompatActivity.bindings(crossinline f: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        f(layoutInflater)
    }

fun AppCompatActivity.setupToolbar(@StringRes resId: Int) {
    with(findViewById<Toolbar>(R.id.toolbar_view)) {
        title = getString(resId)
        setSupportActionBar(this)
        setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
}