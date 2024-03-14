package com.coldblue.mandalart.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import com.coldblue.designsystem.theme.HMColor
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.state.MandaState
import com.coldblue.mandalart.state.MandaType
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R
import com.orhanobut.logger.Logger

object MandaUtils {
    @Composable
    fun getTagList(): List<String> =
        stringArrayResource(id = R.array.tags).toList()

    fun transformToMandaList(
        keys: List<MandaKey>,
        details: List<MandaDetail>
    ): List<MandaState> {

        // 3X3 크기의 큰 박스 만들기
        // key가 존재하는 곳은 3X3 크기의 작은 박스 만들어서 큰 박스에 넣기
        // key 없는 곳은 Empty 박스를 큰 박스에 넣기
        // 0번 인덱스부터 시작해서 4번째 박스는 키박스로 만들기

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
                val (darkColor, lightColor) = indexToDarkLightColor(key.colorIndex)
                val groupIdList = mutableListOf<Int>()
                var isDone = true

                for (id in 1..9) {

                    val detailId = id + keyId*9

                    if (detailIdList.contains(detailId)) {
                        val detail = detailList.removeFirst()
                        groupIdList.add(detail.id)

                        smallList.add(
                            MandaType.Detail(
                                mandaUI = MandaUI(
                                    id = detailId,
                                    darkColor = darkColor,
                                    lightColor = lightColor,
                                    isDone = detail.isDone
                                )
                            )
                        )

                        if(!detail.isDone)
                            isDone = false

                    } else {
                        smallList.add(
                            MandaType.None(
                                mandaUI = MandaUI(id = detailId, darkColor = darkColor)
                            )
                        )
                    }
                }

                val keyType = MandaType.Key(
                    mandaUI = MandaUI(id = 5, darkColor = darkColor, isDone = isDone),
                    groupIdList = groupIdList
                )

                smallList[4] = keyType
                centerList.add(keyType)
                bigList.add(MandaState.Exist(mandaUIList = smallList.toList()))
                smallList.clear()
            } else {

                bigList.add(MandaState.Empty(id = keyId))

            }
        }

        bigList[4] = MandaState.Exist(mandaUIList = centerList.toList())

        Log.e("TAG", "transformToMandaList: $bigList")
        Logger.d(bigList)
        return bigList
    }

    private fun indexToDarkLightColor(index: Int): Pair<Color, Color> {
        return when (index) {
            0 -> Pair(HMColor.Dark.Pink, HMColor.Light.Pink)
            1 -> Pair(HMColor.Dark.Red, HMColor.Light.Red)
            2 -> Pair(HMColor.Dark.Orange, HMColor.Light.Orange)
            3 -> Pair(HMColor.Dark.Yellow, HMColor.Light.Yellow)
            4 -> Pair(HMColor.Dark.Green, HMColor.Light.Green)
            5 -> Pair(HMColor.Dark.Blue, HMColor.Light.Blue)
            6 -> Pair(HMColor.Dark.Indigo, HMColor.Light.Indigo)
            else -> Pair(HMColor.Dark.Purple, HMColor.Light.Purple)
        }
    }

    fun colorToIndex(color: Color): Int {
        return when (color) {
            HMColor.Dark.Pink -> 0
            HMColor.Dark.Red -> 1
            HMColor.Dark.Orange -> 2
            HMColor.Dark.Yellow -> 3
            HMColor.Dark.Green -> 4
            HMColor.Dark.Blue -> 5
            HMColor.Dark.Indigo -> 6
            else -> 7
        }
    }
}
