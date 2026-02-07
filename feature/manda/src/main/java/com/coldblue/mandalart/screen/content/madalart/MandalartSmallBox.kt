package com.coldblue.mandalart.screen.content.madalart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.screen.MandaDetailBox
import com.coldblue.mandalart.screen.MandaEmptyBox
import com.coldblue.mandalart.screen.MandaKeyBox
import com.coldblue.mandalart.state.MandaBottomSheetContentState
import com.coldblue.mandalart.state.MandaBottomSheetContentType
import com.coldblue.mandalart.state.MandaType

@Composable
fun MandalartSmallBox(
    modifier: Modifier,
    smallBox: MandaType,
    bigBoxId: Int,
    isMandaInit: Boolean,
    onInsert: (Boolean, MandaBottomSheetContentState) -> Unit,
    onDialog: () -> Unit,
    isMandaCenter: Boolean,
    setMandaDoneState: (Int, Boolean, Color) -> Unit,
) {

    when (smallBox) {
        is MandaType.None -> {
            MandaEmptyBox(
                modifier = modifier
            ) {
                if (isMandaInit) {
                    onInsert(
                        true, MandaBottomSheetContentState.Insert(
                            if (bigBoxId == 5) {
                                if (smallBox.mandaUI.id == 5) {
                                    MandaBottomSheetContentType.MandaFinal(
                                        smallBox.mandaUI
                                    )
                                } else {
                                    MandaBottomSheetContentType.MandaKey(
                                        smallBox.mandaUI,
                                    )
                                }
                            } else {
                                MandaBottomSheetContentType.MandaDetail(
                                    smallBox.mandaUI,
                                )
                            }
                        )
                    )

                } else {
                    if (bigBoxId == 5 && smallBox.mandaUI.id == 5) {
                        onInsert(
                            true, MandaBottomSheetContentState.Insert(
                                MandaBottomSheetContentType.MandaFinal(
                                    smallBox.mandaUI
                                )
                            )
                        )
                    } else {
                        onDialog()
                    }
                }
            }
        }

        is MandaType.Key -> {
            val smallBoxData = smallBox.mandaUI

            setMandaDoneState(smallBoxData.id - 1, smallBoxData.isDone, smallBoxData.color)

            MandaKeyBox(
                modifier = modifier,
                name = smallBoxData.name,
                backgroundColor = smallBoxData.color,
                borderColor = smallBoxData.color,
                isDone = if (smallBoxData.id == 5) true else smallBoxData.isDone,
                isCenter = isMandaCenter
            ) {
                onInsert(
                    true,
                    if (bigBoxId == 5 && smallBoxData.id == 5) {
                        MandaBottomSheetContentState.Insert(
                            MandaBottomSheetContentType.MandaFinal(
                                smallBoxData
                            )
                        )
                    } else {
                        MandaBottomSheetContentState.Update(
                            MandaBottomSheetContentType.MandaKey(
                                mandaUI = smallBoxData,
                                groupIdList = smallBox.groupIdList
                            )
                        )
                    }
                )
            }
        }

        is MandaType.Detail -> {
            val data = smallBox.mandaUI
            // 전체 달성여부 체크하여 BorderColor 변경
            var isAllDone = false
//            if (bigBox.mandaUIList.size == 9) isAllDone = bigBox.mandaUIList[4].mandaUI.isDone
            MandaDetailBox(
                modifier = modifier,
                name = data.name,
                backgroundColor = data.color,
                borderColor = if (isAllDone) HMColor.Background else data.color,
                isDone = data.isDone
            ) {
                onInsert(
                    true, MandaBottomSheetContentState.Update(
                        MandaBottomSheetContentType.MandaDetail(
                            mandaUI = data
                        )
                    )
                )

            }
        }
    }
}