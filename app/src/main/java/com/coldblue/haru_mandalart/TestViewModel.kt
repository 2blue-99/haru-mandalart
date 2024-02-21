package com.coldblue.haru_mandalart

import androidx.lifecycle.ViewModel
import com.coldblue.data.repo.MandaRepo
import com.coldblue.data.repo.TodoRepo
import com.coldblue.data.repo.UserPreferencesRepo
import com.coldblue.data.util.LoginHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val mandaRepo: MandaRepo,
    private val todoRepo: TodoRepo,
    private val userPreferencesRepo: UserPreferencesRepo,
    private val userHelper: LoginHelper
):ViewModel() {
}