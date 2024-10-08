package com.coldblue.survey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldblue.domain.network.GetNetworkStateUseCase
import com.coldblue.domain.survey.GetSurveyListUseCase
import com.coldblue.model.Survey
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(
    private val getSurveyListUseCase: GetSurveyListUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,

    ) : ViewModel() {
    private val _surveyUIState = MutableStateFlow<SurveyUiState>(SurveyUiState.Loading)
    val surveyUIState: StateFlow<SurveyUiState> get() = _surveyUIState

    private val _survey = MutableStateFlow<Survey?>(null)
    val survey: StateFlow<Survey?> get() = _survey

    fun getSurveyList() {
        viewModelScope.launch {
            if (getNetworkStateUseCase().first()) {
                if(getSurveyListUseCase().isNotEmpty()){
                    _surveyUIState.value = SurveyUiState.Success(getSurveyListUseCase())
                }else{
                    _surveyUIState.value = SurveyUiState.Error("제안하기 게시판이 비어있습니다.")
                }
            } else {
                _surveyUIState.value = SurveyUiState.Error("네트워크 연결 상태가 좋지 않습니다.")
            }
        }
    }
    init {
        getSurveyList()
    }
}