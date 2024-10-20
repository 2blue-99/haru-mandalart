package com.coldblue.domain.survey

import com.coldblue.data.repository.survey.SurveyRepository
import com.coldblue.model.Survey
import javax.inject.Inject

class LikeSurveyUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository
) {
    suspend operator fun invoke(survey: Survey){
        if (survey.isLiked){
            surveyRepository.likeSurvey(survey.id,survey.likeCount)
        }else{
            surveyRepository.likeCancelSurvey(survey.id,survey.likeCount)
        }
    }
}