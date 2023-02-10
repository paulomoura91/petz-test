package com.paulomoura.petztest.cards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulomoura.petztest.cards.model.dto.Card
import com.paulomoura.petztest.cards.model.dto.toCard
import com.paulomoura.petztest.cards.model.repository.CardsRepository
import com.paulomoura.petztest.commons.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardSetsViewModel @Inject constructor(private val repository: CardsRepository) : ViewModel() {

    private val cardSetsMutableLivedata = MutableLiveData<Response<List<String>>>()
    val cardSetsLiveData: LiveData<Response<List<String>>> get() = cardSetsMutableLivedata
    private var cardsMutableLiveData = MutableLiveData<Response<List<Card>>>()
    val cardsLiveData: LiveData<Response<List<Card>>> get() = cardsMutableLiveData

    fun getSets() {
        viewModelScope.launch {
            cardSetsMutableLivedata.value = Response.Loading()
            try {
                val setDTO = repository.getSets()
                if (setDTO.sets == null) throw Exception("Error at getting Card Sets from the server")
                cardSetsMutableLivedata.value = Response.Success(data = setDTO.sets)
            } catch (exception: Exception) {
                cardSetsMutableLivedata.value = Response.Error(error = exception)
            }
        }
    }

    fun getCardsInASet(set: String) {
        viewModelScope.launch {
            cardsMutableLiveData.value = Response.Loading()
            try {
                val cardDTOs = repository.getCardsInASet(set)
                val cards = cardDTOs.map { it.toCard() }
                cardsMutableLiveData.value = Response.Success(data = cards)
            } catch (exception: Exception) {
                cardsMutableLiveData.value = Response.Error(error = exception)
            }
        }
    }

    fun clearCards() { cardsMutableLiveData = MutableLiveData<Response<List<Card>>>() }
}