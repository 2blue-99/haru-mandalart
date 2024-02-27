package com.coldblue.model


sealed interface Manda{
    data class MandaDetail(
        val name: String,
        val mandaId: Int,
        val isDone: Boolean,
        val id: Int = 0
    ): Manda

    data class MandaKey(
        val name: String,
        val colorIndex: Int = 0,
        val id: Int = 0
    ): Manda
}

//interface Manda {
//    val name: String
//    val mandaId: Int
//    val isDone: Boolean
//    val colorIndex: Int
//    val id: Int
//}

