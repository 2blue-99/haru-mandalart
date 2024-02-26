package com.coldblue.mandalart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.mandalart.state.MandaUIState

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel()
){
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        MandaContentWithStatus(mandaUiState)
    }
}

@Composable
fun MandaContentWithStatus(
    mandaUIState: MandaUIState
){
    when(mandaUIState){
        is MandaUIState.Loading -> {}
        is MandaUIState.Error ->  {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent()
        }
        is MandaUIState.InitializedSuccess -> {}
    }
}

@Preview
@Composable
fun Preview(){
    UnInitializedMandaContent()
}
@Composable
fun UnInitializedMandaContent(){
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ){
        item { Text(text = "당신의 최종 목표는 무엇인가요?") }
        item { Text(text = "당신의 최종 목표는 무엇인가요?") }
    }
}

@Composable
fun InitializedMandaContent(){

}

