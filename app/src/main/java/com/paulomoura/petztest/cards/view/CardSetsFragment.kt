package com.paulomoura.petztest.cards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.viewmodel.CardSetsViewModel
import com.paulomoura.petztest.commons.Response
import com.paulomoura.petztest.databinding.FragmentCardSetsBinding
import com.paulomoura.petztest.extensions.bindings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardSetsFragment : Fragment() {

    private val viewModel: CardSetsViewModel by activityViewModels()
    private val binding by bindings(FragmentCardSetsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_card_sets, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.list.layoutManager = GridLayoutManager(context, COLUMN_COUNT)

        viewModel.cardSetsLiveData.value?.let {
            handleResponse(it)
        } ?: run {
            viewModel.cardSetsLiveData.observe(viewLifecycleOwner) {
                handleResponse(it)
            }
            viewModel.getSets()
        }
    }

    private fun handleResponse(response: Response<List<String>>) {
        when (response) {
            is Response.Loading -> showLoading()
            is Response.Success -> loadCardSets(response.data)
            is Response.Error -> showError(response.error)
        }
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
    }

    private fun loadCardSets(cardSets: List<String>?) {
        binding.loadingView.isVisible = false
        cardSets?.let { sets ->
            binding.list.adapter = CardSetsRecyclerViewAdapter(sets) { selectedSet ->
                showCardsList(selectedSet)
            }
        }
    }

    private fun showCardsList(set: String) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, CardsFragment.newInstance(set))
        transaction.addToBackStack(null)
        transaction.commit()
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