package com.paulomoura.petztest.cards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.viewmodel.CardSetsViewModel
import com.paulomoura.petztest.commons.Response
import com.paulomoura.petztest.databinding.FragmentCardSetsBinding
import com.paulomoura.petztest.extensions.bindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardSetsFragment : Fragment() {

    private val viewModel: CardSetsViewModel by viewModels()
    private val binding by bindings(FragmentCardSetsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_sets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        viewModel.cardSetsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> showLoading()
                is Response.Success -> loadCardSets(it.data)
                is Response.Error -> showError(it.error)
            }
        }
        viewModel.getSets()
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
    }

    private fun loadCardSets(cardSets: List<String>?) {
        binding.loadingView.isVisible = false
        cardSets?.let {
            binding.list.adapter = CardSetsRecyclerViewAdapter(it)
        }
    }

    private fun showError(throwable: Throwable?) {
        binding.loadingView.isVisible = false
        Toast.makeText(requireContext(), "Error: ${throwable?.localizedMessage}", Toast.LENGTH_LONG ).show()
    }

    companion object {
        private const val COLUMN_COUNT = 2

        @JvmStatic
        fun newInstance() = CardSetsFragment()
    }
}