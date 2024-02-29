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

        val doneArr = BooleanArray(9) { true }

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
                    typeList.add(MandaType.Done(detail.asMandaUI(colors)))
                else
                    typeList.add(MandaType.DetailStart(detail.asMandaUI(colors)))

            } else {
                typeList.add(MandaType.None(MandaUI(id = id)))
            }

            // 9개 채우면 그룹으로 만들어서 ResultList에 삽입
            if (typeList.size == 9) {
                if (keyIdList.contains(keyIndex)) {
                    val key = keyList.removeFirst()
                    val colors = indexToDarkLightColor(key.colorIndex)
                    if(doneArr[keyIndex]) {
                        typeList[4] = MandaType.Done(key.asMandaUI(colors))
                        keyGroupList.add(MandaType.Done(key.asMandaUI(colors)))
                    }
                    else {
                        typeList[4] = MandaType.KeyStart(key.asMandaUI(colors))
                        keyGroupList.add(MandaType.KeyStart(key.asMandaUI(colors)))
                    }

                    resultList.add(MandaState.Exist(typeList.toList()))
                }else{
                    resultList.add(MandaState.Empty(keyIndex))
                }
                typeList.clear()
                keyIndex++
            }
        }

        // 센터에 있는 키 만다 수정 로직
        for(id in 1..9){
            resultList[4] = MandaState.Exist(keyGroupList)
        }

//        for (id in 1..81) {
//            var color: Pair<Color, Color> = Pair(HMColor.Dark.Pink, HMColor.Light.Pink)
//            //핵심
//            if (id % 9 == 5) {
//                if (keyIdList.contains(keyIndex)) {
//                    val key = keyList.removeFirst()
//                    if (doneArr[keyIndex-1])
//                        typeList.add(MandaType.Done(MandaUI(name = key.name, id = , outlineColor = , fillColor = )))
////                        typeList.add(MandaType.Done(name = keyList.first().name, id = keyIndex))
//                    else
//                        typeList.add(MandaType.Outline(MandaUI(name = , id = , outlineColor = , fillColor = )))
////                        typeList.add(MandaType.Outline(name = keyList.first().name, id = keyIndex))
//
//                    color = indexToMandaColor(keyList.first().colorIndex)
//                }
//                else
//                    typeList.add(MandaType.None(MandaUI(name = , id = , outlineColor = , fillColor = )))
////                    typeList.add(MandaType.None(id = keyIndex))
//            } else {
//                if (detailIdList.contains(id)) {
//                    if (details[id - 1].isDone) {
//                        typeList.add(MandaType.Done(name = details.first().name, id = details.first().id))
//                        doneArr[keyIndex-1] = false
//                    }
//                    else
//                        typeList.add(MandaType.Fill(name = details.first().name, id = details.first().id))
//
//                    detailIdList.removeFirst()
//                    detailList.removeFirst()
//                } else {
//                    typeList.add(MandaType.None(id = id - keyIndex + 1))
//                    doneArr[keyIndex-1] = false
//                }
//            }
//
//            if (id % 9 == 0) {
//                if (keyIdList.contains(keyIndex)) {
//                    resultList.add(
//                        MandaState.Exist(
//                            darkColor = color.first,
//                            lightColor = color.second,
//                            mandaUIList = typeList.toList()
//                        )
//                    )
//                    keyIdList.removeFirst()
//                } else {
//                    resultList.add(MandaState.Empty(id = keyIndex))
//                }
//                typeList.clear()
//                keyIndex++
//            }
//        }

        // 중앙에 있는 값 수정
//            for (id in 44..53) {
//                val keyResult = mutableListOf<MandaType>()
//                repeat(9) {
//                    if (keyIdList.contains(it)) {
//                        if (it == 4 || doneArr[it])
//                            keyResult.add(MandaType.DetailStart(id = it + 1, name = keys[it].name))
//                        else
//                            keyResult.add(MandaType.KeyStart(id = it + 1, name = keys[it].name))
//                    } else {
//                        keyResult.add(MandaType.None(id = it + 1))
//                    }
//                }
//                resultList[5] = MandaState.Exist(keyResult.toList())
//            }

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
}
