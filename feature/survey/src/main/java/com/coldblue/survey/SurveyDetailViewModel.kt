package com.coldblue.survey

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.survey.GetSurveyUseCase
import com.coldblue.model.Survey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

    private val getSurveyUseCase: GetSurveyUseCase
) : ViewModel() {

    private val id: Int? = savedStateHandle.get<Int>("id")


    private val _survey = MutableStateFlow<Survey?>(null)
    val survey: StateFlow<Survey?> get() = _survey

    init {
        viewModelScope.launch {
            if (id != null) {
                _survey.value = getSurveyUseCase(id)
            }
        }
    }

    fun likeSurvey(id: Int) {

    }

}