package com.coldblue.mandalart.screen.content.madalart


import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.toSize
import com.coldblue.mandalart.state.MandaGestureState
import com.orhanobut.logger.Logger
import kotlin.math.abs

class MandalartGestureController() {

    var scaleX by mutableFloatStateOf(1f)
    var scaleY by mutableFloatStateOf(1f)

    var translateX by mutableFloatStateOf(0f)
    var translateY by mutableFloatStateOf(0f)

    var isGesture by mutableStateOf(false)

    var isZoom by mutableStateOf(false)

    private var offsetX = 0f
    private var offsetY = 0f

    private var direction = MandaGestureState.Down

    var mandaSize: Size = Size.Zero

    var widthList = listOf(0f, 0f, 0f)
    var heightList = listOf(0f, 0f, 0f)

    private val gestureAccuracy = 100f

    fun initMandaSize(size: Size, width: Float, height: Float) {
        mandaSize = size
        widthList = listOf(width, 0f, -width)
        heightList = listOf(height, 0f, -height)
    }

    fun dragStartDetector(dragAmount: Offset) {
        val (x, y) = dragAmount
        if (abs(x) > abs(y)) {
            offsetX += x
            direction = if (x > 0) MandaGestureState.Left else MandaGestureState.Right
        } else {
            offsetY += y
            direction = if (y > 0) MandaGestureState.Up else MandaGestureState.Down
        }
    }

    fun onDragEnd(currentIndex: Int, changeCurrentIndex: (Int) -> Unit) {
        isGesture = true
        scaleX = 3f
        scaleY = 3f

        when (direction) {
            MandaGestureState.Left -> {
                if (offsetX > gestureAccuracy && translateX < mandaSize.width) {
                    translateX += mandaSize.width
                    changeCurrentIndex(currentIndex - 1)
                }
            }

            MandaGestureState.Right -> {
                if (offsetX < -gestureAccuracy && translateX > -mandaSize.width) {
                    translateX -= mandaSize.width
                    changeCurrentIndex(currentIndex + 1)
                }
            }

            MandaGestureState.Up -> {
                if (offsetY > gestureAccuracy && translateY < mandaSize.height) {
                    translateY += mandaSize.height
                    changeCurrentIndex(currentIndex - 3)
                }
            }

            MandaGestureState.Down -> {
                if (offsetY < -gestureAccuracy && translateY > -mandaSize.height) {
                    translateY -= mandaSize.height
                    changeCurrentIndex(currentIndex + 3)
                }
            }

            MandaGestureState.ZoomOut -> {

            }
        }
        offsetX = 0f
        offsetY = 0f
    }

    fun zoom(index: Int) {
        if (index == -1) {
            reset()
            return
        }
        isZoom = true
        scaleX = 3f
        scaleY = 3f
        translateX += widthList[index % 3]
        translateY += heightList[index / 3]
    }

    fun reset() {
        isZoom = false
        scaleX = 1f
        scaleY = 1f
        translateX = 0f
        translateY = 0f
    }
}