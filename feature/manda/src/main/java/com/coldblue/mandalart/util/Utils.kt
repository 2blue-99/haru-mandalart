package com.coldblue.mandalart.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
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
                detailMapList.add(MandaType.Done(details[it]))
            else {
                detailMapList.add(MandaType.Fill(details[it]))
                isDone = false
            }
        else {
            detailMapList.add(MandaType.Empty(details[it]))
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
                keyMapList.add(MandaType.Done(keys[it]))
            else
            keyMapList.add(MandaType.Fill(keys[it]))
        else
            keyMapList.add(MandaType.Empty(keys[it]))
    }

    return Pair(keyMapList, detailMapList)
}
//fun List<MandaKey>.transformToMap(): List<Map<Int, MandaKey>> {
//    val result = mutableListOf<Map<Int, MandaKey>>()
//
//
//}
//
//fun List<MandaDetail>.transformToMap(): List<Map<Int, MandaDetail>> {
//    val result = mutableListOf<Map<Int, MandaDetail>>()
//    val doneList = this.map { it.isDone }
//    val idList = this.map { it.id }
//    repeat(64) {
//        if (idList.contains(it))
//            if (doneList[it])
//                result.add(mapOf(2 to this[it]))
//            else
//                result.add(mapOf(1 to this[it]))
//        else
//            result.add(mapOf(0 to this[it]))
//    }
//    return result
//}