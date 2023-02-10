package com.paulomoura.petztest.cards.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulomoura.petztest.R
import com.paulomoura.petztest.cards.model.dto.Card
import com.paulomoura.petztest.cards.viewmodel.CardSetsViewModel
import com.paulomoura.petztest.commons.Response
import com.paulomoura.petztest.databinding.FragmentCardsBinding
import com.paulomoura.petztest.extensions.bindings

@Suppress("NAME_SHADOWING")
class CardsFragment : Fragment() {

    private val viewModel: CardSetsViewModel by activityViewModels()
    private val binding by bindings(FragmentCardsBinding::bind)

    private var set: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            set = it.getString(ARG_SET)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cards, container, false)
    }

    override fun onStart() {
        super.onStart()
        setOnBackPressedCallback()

        binding.list.layoutManager = LinearLayoutManager(context)
        viewModel.cardsLiveData.value?.let {
            handleResponse(it)
        } ?: run {
            viewModel.cardsLiveData.observe(viewLifecycleOwner) {
                handleResponse(it)
            }
            set?.let { viewModel.getCardsInASet(it) }
        }
    }

    private fun setOnBackPressedCallback() {
        with(requireActivity().onBackPressedDispatcher) {
            addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        viewModel.clearCards()
                        parentFragmentManager.popBackStack()
                    }
                }
            )
        }
    }

    private fun handleResponse(response: Response<List<Card>>) {
        when (response) {
            is Response.Loading -> showLoading()
            is Response.Success -> loadCards(response.data)
            is Response.Error -> showError(response.error)
        }
    }

    private fun showLoading() {
        binding.loadingView.isVisible = true
    }

    private fun loadCards(cards: List<Card>?) {
        binding.loadingView.isVisible = false
        cards?.let { cards ->
            binding.list.adapter = CardsRecyclerViewAdapter(cards) { selectedCard ->
                showCardDetail(selectedCard)
            }
        }
    }

    private fun showCardDetail(card: Card) {

    }

    private fun showError(throwable: Throwable?) {
        binding.loadingView.isVisible = false
        Toast.makeText(requireContext(), "Error: ${throwable?.localizedMessage}", Toast.LENGTH_LONG ).show()
    }

    companion object {
        private const val ARG_SET = "ARG_SET"

        @JvmStatic
        fun newInstance(set: String) =
            CardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SET, set)
                }
            }
    }
}