package com.coldblue.mandalart.util

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
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

        val detailIdList = details.map { it.id }.toMutableList()
        val keyIdList = keys.map { it.id }.toMutableList()
        val doneArr = BooleanArray(9) { true }
        val colorArr = IntArray(9)

        // 9개 들어갈 전체 만다 리스트
        val resultList = mutableListOf<MandaState>()
        // 9개 들어갈 세부 리스트
        val typeList = mutableListOf<MandaType>()

        var keyIndex = 1


        for (id in 1..81) {
            //핵심
            if (id % 9 == 5) {
                // DB에 값이 있다면
                if (keyIdList.contains(keyIndex)) {
                    // Done
                    if (doneArr[keyIndex])
                        typeList.add(
                            MandaType.Done(
                                name = keys[keyIndex].name,
                                id = keyIndex
                            )
                        )
                    else
                        typeList.add(
                            MandaType.Fill(
                                name = keys[keyIndex].name,
                                id = keyIndex
                            )
                        )
                    colorArr[keyIndex - 1] = keys[keyIndex].colorIndex
                    keyIdList.remove(keyIndex)
                } else {
                    // Empty
                    typeList.add(MandaType.Empty(id = keyIndex))
                }
                keyIndex++
            } else {
                if (detailIdList.contains(id)) {
                    if (details[id - 1].isDone) {
                        typeList.add(
                            MandaType.Done(
                                name = details[id - 1].name,
                                mandaId = details[id - 1].mandaId,
                                id = details[id - 1].id
                            )
                        )
                        doneArr[id % 9 + keyIndex] = false
                    } else
                        typeList.add(
                            MandaType.Fill(
                                name = details[id - 1].name,
                                mandaId = details[id - 1].mandaId,
                                id = details[id - 1].id
                            )
                        )
                    detailIdList.remove(id)
                } else {
                    typeList.add(MandaType.Empty(id - keyIndex + 1))
                    doneArr[id % 9 + keyIndex] = false
                }
            }

            if (id % 9 == 0) {
                if (typeList.isEmpty())
                    resultList.add(MandaState.Empty(id = keyIndex))
                else
                    // TODO colorArr에 색 인덱스가 들어있으니, 색 인덱스 만들어서 합쳐보자
                    resultList.add(
                        MandaState.Exist(
                            outlineColor = null,
                            fillColor = null,
                            mandaUIList = typeList
                        )
                    )
            }
        }
        Log.e("TAG", "transformToMandaList: $resultList", )
        return resultList
    }
}