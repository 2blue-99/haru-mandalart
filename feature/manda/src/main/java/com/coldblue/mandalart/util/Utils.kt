package com.coldblue.mandalart.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import com.coldblue.mandalart.model.MandaUI
import com.coldblue.mandalart.model.asMandaUI
import com.coldblue.mandalart.state.MandaType
import com.coldblue.model.MandaDetail
import com.coldblue.model.MandaKey
import com.colddelight.mandalart.R


@Composable
fun getTagList(): List<String> =
    stringArrayResource(id = R.array.tags).toList()


fun transformToMap(
    keys: List<MandaKey>,
    details: List<MandaDetail>
): Pair<List<MandaType>, List<MandaType>> {
    val doneArr = BooleanArray(8){true}
    val detailMapList = mutableListOf<MandaType>()
    val keyMapList = mutableListOf<MandaType>()

    val detailDoneList = details.map { it.isDone }
    val detailIdList = details.map { it.id }
    val keyIdList = keys.map { it.id }
    var isDone = true

    repeat(64) {
        if (detailIdList.contains(it))
            if (detailDoneList[it])
                detailMapList.add(MandaType.Done(details[it].asMandaUI()))
            else {
                detailMapList.add(MandaType.Fill(details[it].asMandaUI()))
                isDone = false
            }
        else {
            detailMapList.add(MandaType.Empty(MandaUI(id = it)))
            isDone = false
        }

        if((it+1) % 8 == 0){
            if(isDone)
                doneArr[(it+1 / 8)-1] = true
            isDone = false
        }
    }

    repeat(8){
        if(keyIdList.contains(it))
            if(doneArr[it])
                keyMapList.add(MandaType.Done(keys[it].asMandaUI()))
            else
            keyMapList.add(MandaType.Fill(keys[it].asMandaUI()))
        else
            keyMapList.add(MandaType.Empty(MandaUI(id = it)))
    }

    return Pair(keyMapList, detailMapList)
}