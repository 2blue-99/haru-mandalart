package com.coldblue.mandalart.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.model.asMandaUI
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R

object MandaUtils {
    @Composable
    fun getTagList(): List<String> =
        stringArrayResource(id = R.array.tags).toList()

    fun transformToMandaList(
        keys: List<MandaKey>,
        details: List<MandaDetail>
    ): List<MandaState> {

        val keyList = keys.toMutableList()
        val detailList = details.toMutableList()

        val detailIdList = details.map { it.id }.toMutableList()
        val keyIdList = keys.map { it.id }.toMutableList()

        val doneArr = BooleanArray(10) { true }

        val resultList = mutableListOf<MandaState>()
        val typeList = mutableListOf<MandaType>()
        val keyGroupList = mutableListOf<MandaType>()

        var keyIndex = 1

        for (id in 1..81) {
            if (detailIdList.contains(id)) {

                val detail = detailList.removeFirst()
                val colors = indexToDarkLightColor(detail.colorIndex)

                doneArr[keyIndex] = false

                if (detail.isDone)
                    typeList.add(MandaType.Done(detail.asMandaUI(colors, true)))
                else
                    typeList.add(MandaType.DetailStart(detail.asMandaUI(colors, false)))

            } else {
                typeList.add(MandaType.None(MandaUI(id = id)))
            }

            // 9개 채우면 그룹으로 만들어서 ResultList에 삽입
            if (typeList.size == 9) {
                if (keyIdList.contains(keyIndex)) {
                    val key = keyList.removeFirst()
                    val colors = indexToDarkLightColor(key.colorIndex)
                    if(doneArr[keyIndex]) {
                        typeList[4] = MandaType.Done(key.asMandaUI(colors, true))
                        keyGroupList.add(MandaType.Done(key.asMandaUI(colors, true)))
                    }
                    else {
                        typeList[4] = MandaType.KeyStart(key.asMandaUI(colors, false))
                        keyGroupList.add(MandaType.KeyStart(key.asMandaUI(colors, false)))
                    }

                    resultList.add(MandaState.Exist(typeList.toList()))
                }else{
                    resultList.add(MandaState.Empty(keyIndex))
                    keyGroupList.add(MandaType.None(MandaUI(id=keyIndex)))
                }
                typeList.clear()
                keyIndex++
            }
        }

        // 최종 목표 만다 수정
        keyGroupList[4].mandaUI.darkColor = HMColor.Primary
        keyGroupList[4].mandaUI.lightColor = HMColor.Primary

        // 센터에 있는 키 만다 수정 로직
        repeat(9){
            resultList[4] = MandaState.Exist(keyGroupList.toList())
        }

        Log.e("TAG", "transformToMandaList: $resultList")
        return resultList
    }

    private fun indexToDarkLightColor(index: Int): Pair<Color, Color> {
        return when (index) {
            1 -> Pair(HMColor.Dark.Pink, HMColor.Light.Pink)
            2 -> Pair(HMColor.Dark.Red, HMColor.Light.Red)
            3 -> Pair(HMColor.Dark.Orange, HMColor.Light.Orange)
            4 -> Pair(HMColor.Dark.Yellow, HMColor.Light.Yellow)
            5 -> Pair(HMColor.Dark.Green, HMColor.Light.Green)
            6 -> Pair(HMColor.Dark.Blue, HMColor.Light.Blue)
            7 -> Pair(HMColor.Dark.Indigo, HMColor.Light.Indigo)
            else -> Pair(HMColor.Dark.Purple, HMColor.Light.Purple)
        }
    }

    fun colorToIndex(color: Color): Int {
        return when (color) {
            HMColor.Dark.Pink -> 1
            HMColor.Dark.Red -> 2
            HMColor.Dark.Orange -> 3
            HMColor.Dark.Yellow -> 4
            HMColor.Dark.Green -> 5
            HMColor.Dark.Blue -> 6
            HMColor.Dark.Indigo -> 7
            else -> 8
        }
    }
}
