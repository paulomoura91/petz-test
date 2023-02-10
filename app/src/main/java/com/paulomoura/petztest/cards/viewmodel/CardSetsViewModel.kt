package com.paulomoura.petztest.cards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paulomoura.petztest.cards.model.repository.CardsRepository
import com.paulomoura.petztest.commons.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardSetsViewModel @Inject constructor(private val repository: CardsRepository) : ViewModel() {

    private val cardSetsMutableLivedata = MutableLiveData<Response<List<String>>>()
    val cardSetsLiveData: LiveData<Response<List<String>>> get() = cardSetsMutableLivedata

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
}