package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import javax.inject.Inject

class GetSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(id: Int) = surveyRepository.getSurvey(id)
}