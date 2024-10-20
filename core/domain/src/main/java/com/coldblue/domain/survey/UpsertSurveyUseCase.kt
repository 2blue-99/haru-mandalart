package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import com.coldblue.model.Survey
import javax.inject.Inject

class UpsertSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(survey: Survey) {
        surveyRepository.upsertSurvey(survey)
    }
}