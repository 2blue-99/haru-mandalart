package com.coldblue.mandalart.screen

import android.content.Context
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.coldblue.mandalart.ExplainViewModel
import com.coldblue.mandalart.MandaViewModel
import com.coldblue.mandalart.UpdateNoteViewModel
import com.coldblue.mandalart.screen.content.InitializedMandaContent
import com.coldblue.mandalart.screen.content.UnInitializedMandaContent
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetUIState
import com.coldblue.mandalart.state.MandaUIState
import com.coldblue.mandalart.state.MandaUpdateDialogState
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

//const val UPDATE_REQUEST_CODE = 777

@Composable
fun MandaScreen(
    mandaViewModel: MandaViewModel = hiltViewModel(),
    updateNoteViewModel: UpdateNoteViewModel = hiltViewModel(),
    explainViewModel: ExplainViewModel = hiltViewModel(),
    navigateToSetting: () -> Unit,
) {
    val mandaExplainUiState by explainViewModel.mandaExplainUIState.collectAsStateWithLifecycle()
    val mandaUpdateUiState by updateNoteViewModel.mandaUpdateDialogUIState.collectAsStateWithLifecycle()
    val mandaUiState by mandaViewModel.mandaUiState.collectAsStateWithLifecycle()
    val bottomSheetUiState by mandaViewModel.mandaBottomSheetUIState.collectAsStateWithLifecycle()
    val focus = LocalFocusManager.current
    val context = LocalContext.current

    when (val uiState = mandaUpdateUiState) {
        is MandaUpdateDialogState.Show -> {
            UpdateDialog(
                updateNote = uiState.updateNote,
                onUpdate = updateNoteViewModel::showPlayStore,
                onDismiss = { updateNoteViewModel.changeUpdateNoteDialog(true, null) }
            )
        }

        else -> { /*TODO  인터넷 연결 X */ }
    }

    LaunchedEffect(Unit) {
        checkUpdate(context) { updateNoteViewModel.getUpdateNote() }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focus.clearFocus()
                })
            }
    ) {
        if (!mandaExplainUiState) {
            MandaExplainPage(
                updateExplainState = explainViewModel::updateExplainState
            )
        } else {
            MandaContentWithState(
                mandaUIState = mandaUiState,
                mandaBottomSheetUiState = bottomSheetUiState,
                updateInitState = mandaViewModel::updateMandaInitState,
                upsertFinalManda = mandaViewModel::upsertMandaFinal,
                upsertMandaKey = mandaViewModel::upsertMandaKey,
                upsertMandaDetail = mandaViewModel::upsertMandaDetail,
                deleteMandaKey = mandaViewModel::deleteMandaKey,
                deleteMandaDetail = mandaViewModel::deleteMandaDetail,
                deleteMandaAll = mandaViewModel::deleteMandaAll,
                changeBottomSheet = mandaViewModel::changeBottomSheet,
                navigateToSetting = navigateToSetting,
                goPlayStore = updateNoteViewModel::showPlayStore
            )
        }
    }
}

@Composable
fun MandaContentWithState(
    mandaUIState: MandaUIState,
    mandaBottomSheetUiState: MandaBottomSheetUIState,
    updateInitState: (Boolean) -> Unit,
    upsertFinalManda: (MandaKey) -> Unit,
    upsertMandaKey: (MandaKey) -> Unit,
    upsertMandaDetail: (MandaDetail) -> Unit,
    deleteMandaKey: (Int, List<Int>) -> Unit,
    deleteMandaDetail: (Int) -> Unit,
    deleteMandaAll: () -> Unit,
    changeBottomSheet: (Boolean, MandaBottomSheetContentState?) -> Unit,
    navigateToSetting: () -> Unit,
    goPlayStore: () -> Unit
) {
    when (mandaUIState) {
        is MandaUIState.Loading -> {}
        is MandaUIState.Error -> {}
        is MandaUIState.UnInitializedSuccess -> {
            UnInitializedMandaContent(
                updateInitState = updateInitState,
                insertFinalManda = upsertFinalManda
            )
        }

        is MandaUIState.InitializedSuccess -> {
            InitializedMandaContent(
                uiState = mandaUIState,
                mandaBottomSheetUIState = mandaBottomSheetUiState,
                upsertMandaFinal = upsertFinalManda,
                upsertMandaKey = upsertMandaKey,
                upsertMandaDetail = upsertMandaDetail,
                deleteMandaKey = deleteMandaKey,
                deleteMandaDetail = deleteMandaDetail,
                deleteMandaAll = deleteMandaAll,
                changeBottomSheet = changeBottomSheet,
                navigateToSetting = navigateToSetting,
                goPlayStore = goPlayStore
            )

        }
    }
}

private fun checkUpdate(
    context: Context,
    onUpdate: () -> Unit
) {
    onUpdate()
    val appUpdateManager = AppUpdateManagerFactory.create(context)
    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
//    val updateLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.StartIntentSenderForResult()
//    ) { result ->
//        // handle callback
//        if (result.data == null) return@rememberLauncherForActivityResult
//        if (result.resultCode == UPDATE_REQUEST_CODE) {
//            Logger.d(result.resultCode)
////            Toast.makeText(context, "Downloading stated", Toast.LENGTH_SHORT).show()
////            if (result.resultCode != Activity.RESULT_OK) {
////                Toast.makeText(context, "Downloading failed" , Toast.LENGTH_SHORT).show()            FlashLightApp.appContext.toast { getString(R.string.update_failed) }
////            }
//        }
//    }
    appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->

//        Logger.d(appUpdateInfo.updateAvailability())
//        Logger.d(UpdateAvailability.UPDATE_AVAILABLE)
//        Logger.d(appUpdateInfo.clientVersionStalenessDays())
        // 업데이트 할게 있는지 체크
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            // 몇번 물어봤는지 체크 + 업데이트 불가능하면 NULL
            && (appUpdateInfo.clientVersionStalenessDays() ?: -1) >= 1
            // 즉시, 유연한 업데이트 가능한지 체크
            && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
        ) {
            onUpdate()
//            val updateResultStarter =
//                IntentSenderForResultStarter { intent, _, fillInIntent, flagsMask, flagsValues, _, _ ->
//                    val request = IntentSenderRequest.Builder(intent)
//                        .setFillInIntent(fillInIntent)
//                        .setFlags(flagsValues, flagsMask)
//                        .build()
//                    updateLauncher.launch(request)
//                }
//
//            appUpdateManager.startUpdateFlowForResult(
//                appUpdateInfo,
//                updateResultStarter,
//                AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build(),
//                UPDATE_REQUEST_CODE
//            )
        }

    }
}


