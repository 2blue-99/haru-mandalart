package com.coldblue.mandalart.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MAX_MANDA_CNT
import com.coldblue.mandalart.state.MandaInfo
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.coldblue.model.MandaTodo
import com.colddelight.mandalart.R
import com.orhanobut.logger.Logger

object MandaUtils {

    fun transformToMandaList(
        keys: List<MandaKey>,
        details: List<MandaDetail>
    ): List<MandaState> {

        val keyList = keys.toMutableList()
        val detailList = details.toMutableList()

        val detailIdList = details.map { it.id }.toMutableList()
        val keyIdList = keys.map { it.id }.toMutableList()

        val bigList = mutableListOf<MandaState>()
        val smallList = mutableListOf<MandaType>()

        val centerList = mutableListOf<MandaType>()

        for (keyId in 1..9) {

            if (keyIdList.contains(keyId)) {
                val key = keyList.removeFirst()
                val color = indexToDarkLightColor(key.colorIndex)
                val groupIdList = mutableListOf<Int>()
                // 디테일 만다라트가 하나라도 done이 아닐경우 false
                // 디테일 만다라트가 하나도 없을 경우 false

                for (id in 1..9) {

                    val detailId = id + (keyId - 1) * 9

                    if (detailIdList.contains(detailId)) {
                        val detail = detailList.removeFirst()
                        groupIdList.add(detail.id)

                        smallList.add(
                            MandaType.Detail(
                                mandaUI = MandaUI(
                                    id = detailId,
                                    name = detail.name,
                                    color = color,
                                    originId = detail.originId,
                                    isDone = detail.isDone
                                )
                            )
                        )
                    } else {
                        smallList.add(
                            MandaType.None(
                                mandaUI = MandaUI(id = detailId, color = color)
                            )
                        )
                    }
                }

                val keyType = MandaType.Key(
                    mandaUI = MandaUI(
                        name = key.name,
                        color = if (keyId == 5) HMColor.LightPrimary else color,
                        isDone = keyId == 5,
                        originId = key.originId,
                        id = keyId
                    ),
                    groupIdList = groupIdList
                )
                smallList[4] = keyType
                centerList.add(keyType)
                bigList.add(
                    MandaState.Exist(
                        id = keyId,
                        mandaUIList = smallList.toList()
                    )
                )
                smallList.clear()
            } else {
                bigList.add(MandaState.Empty(id = keyId))
                centerList.add(MandaType.None(mandaUI = MandaUI(id = keyId)))
            }
        }

        bigList[4] = MandaState.Exist(
            id = 5,
            mandaUIList = centerList.toList()
        )
        return bigList

    }

    private fun indexToDarkLightColor(index: Int): Color {
        return when (index) {
            0 -> HMColor.DarkPastel.Pink
            1 -> HMColor.DarkPastel.Red
            2 -> HMColor.DarkPastel.Orange
            3 -> HMColor.DarkPastel.Yellow
            4 -> HMColor.DarkPastel.Green
            5 -> HMColor.DarkPastel.Blue
            6 -> HMColor.DarkPastel.Mint
            else -> HMColor.DarkPastel.Purple
        }
    }

    fun colorToIndex(color: Color): Int {
        return when (color) {
            HMColor.DarkPastel.Pink -> 0
            HMColor.DarkPastel.Red -> 1
            HMColor.DarkPastel.Orange -> 2
            HMColor.DarkPastel.Yellow -> 3
            HMColor.DarkPastel.Green -> 4
            HMColor.DarkPastel.Blue -> 5
            HMColor.DarkPastel.Mint -> 6
            else -> -1
        }
    }

    @Composable
    fun getTagList(): List<String> =
        stringArrayResource(id = R.array.tags).toList()

    fun calculatePercentage(index: Int, mandaDetails: List<MandaDetail>): Float {
        return when (index) {
            -1, 4 -> (mandaDetails.count { it.isDone } / mandaDetails.size.toFloat()).takeIf { !it.isNaN() }
                ?: 0f

            else -> {
                val rangeList = mandaDetails.filter { it.id in 9 * index..8 + 9 * index }
                (rangeList.count { it.isDone } / rangeList.size.toFloat()).takeIf { !it.isNaN() }
                    ?: 0f
            }
        }
    }

    fun matchingPercentageColor(index: Int, list: List<MandaState>): Color {
        return when (index) {
            -1, 4 -> HMColor.Primary
            else -> {
                when (val target = list[index]) {
                    is MandaState.Exist -> target.mandaUIList[index].mandaUI.color
                    is MandaState.Empty -> HMColor.Gray
                }
            }
        }
    }

    fun currentColorList(list: List<MandaState>): List<Color?> {
        val colorList = list.map {
            when (it) {
                is MandaState.Exist -> {
                    it.mandaUIList[4].mandaUI.color
                }

                is MandaState.Empty -> {
                    null
                }
            }
        }
        return colorList
    }

    fun matchingTitleManda(index: Int, list: List<MandaState>): MandaUI {
        return when (index) {
            -1, 4 -> (list[4] as MandaState.Exist).mandaUIList[4].mandaUI
            else -> {
                when (val target = list[index]) {
                    is MandaState.Exist -> target.mandaUIList[4].mandaUI
                    is MandaState.Empty -> MandaUI(id = target.id)
                }
            }
        }
    }

    /**
     * 다른 앱 위 표시 권한 요청 설정 여부 확인
     */
    fun checkAlertWindowPermission(context: Context): Boolean = Settings.canDrawOverlays(context)

    /**
     * 다른 앱 위 표시 권한 요청
     */
    fun requestPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + context.packageName)
        )
        context.startActivity(intent)
    }

    fun mandaChangeInfo(mandaKeys:List<MandaKey>): List<MandaInfo>{
        val filteredManda = mandaKeys.filter { (it.id-5) % 9 == 0 }.map { MandaInfo(it.name,it.id,false) }
        val requiredIds = List(MAX_MANDA_CNT) { 5 + 9 * it }
        val missing = requiredIds.filter { id -> filteredManda.none { it.index == id } }
            .map { id -> MandaInfo("",id,true) }
        return (filteredManda + missing).sortedBy { it.index }
    }

    fun mandaKey1to9(mandaKey: List<MandaKey>, currentManda: Int): List<MandaKey> {
        return mandaKey.filter { it.id > currentManda * 9 && it.id <= (currentManda + 1) * 9 }
            .map {
                it.copy(
                    id = if (currentManda != 0) {
                        if (it.id % 9 != 0) it.id % 9 else 9
                    } else it.id
                )
            }
    }

    fun mandaTodo1to9(mandaTodo: List<MandaTodo>, currentManda: Int): List<MandaTodo> {
        return mandaTodo.filter { it.mandaIndex + 1 > currentManda * 9 && it.mandaIndex + 1 <= (currentManda + 1) * 9 }
            .map {
                it.copy(
                    mandaIndex = if (currentManda != 0) {
                        if ((it.mandaIndex + 1) % 9 != 0) ((it.mandaIndex + 1) % 9) - 1 else 9 - 1
                    } else it.mandaIndex
                )
            }
    }

    fun mandaDetail1to81(
        mandaDetail: List<MandaDetail>,
        currentManda: Int
    ): List<MandaDetail> {
        return mandaDetail.filter { it.id > currentManda * 81 && it.id <= (currentManda + 1) * 81 }
            .map {
                it.copy(
                    id = if (currentManda != 0) {
                        if (it.id % 81 != 0) it.id % 81 else 81
                    } else it.id
                )
            }

    }
}
