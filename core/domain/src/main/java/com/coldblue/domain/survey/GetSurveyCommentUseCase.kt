package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import javax.inject.Inject

class GetSurveyCommentUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(surveyId:Int) =
        surveyRepository.getSurveyCommentList(surveyId).sortedWith(compareBy { it.date }).reversed()
}