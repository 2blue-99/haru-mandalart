package com.coldblue.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.survey.GetSurveyListUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase
) : ViewModel() {
    private val _surveyUIState = MutableStateFlow<SurveyUiState>(SurveyUiState.Loading)
    val surveyUIState: StateFlow<SurveyUiState> get() = _surveyUIState

    init {
        viewModelScope.launch {
            _surveyUIState.value = SurveyUiState.Success(getSurveyListUseCase())
        }
    }
    fun likeSurvey(id: Int) {

    }

}