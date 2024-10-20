package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import com.coldblue.model.SurveyComment
import javax.inject.Inject

class UpsertSurveyCommentUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(surveyComment: SurveyComment) {
        surveyRepository.upsertSurveyCommentList(surveyComment)
    }
}