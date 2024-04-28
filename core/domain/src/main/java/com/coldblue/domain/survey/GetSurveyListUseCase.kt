package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import javax.inject.Inject

class GetSurveyListUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke() = surveyRepository.getSurveyList()
}