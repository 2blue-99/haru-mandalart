package com.coldblue.designsystem.iconpack.todo

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack

public val IconPack.Alarm: ImageVector
    get() {
        if (_alarm != null) {
            return _alarm!!
        }
        _alarm = Builder(
            name = "Alarm-clock-check", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(5.0f, 3.0f)
                lineTo(2.0f, 6.0f)
                moveTo(22.0f, 6.0f)
                lineTo(19.0f, 3.0f)
                moveTo(6.0f, 19.0f)
                lineTo(4.0f, 21.0f)
                moveTo(18.0f, 19.0f)
                lineTo(20.0f, 21.0f)
                moveTo(9.0f, 13.5f)
                lineTo(11.0f, 15.5f)
                lineTo(15.5f, 11.0f)
                moveTo(12.0f, 21.0f)
                curveTo(14.1217f, 21.0f, 16.1566f, 20.1571f, 17.6569f, 18.6569f)
                curveTo(19.1571f, 17.1566f, 20.0f, 15.1217f, 20.0f, 13.0f)
                curveTo(20.0f, 10.8783f, 19.1571f, 8.8434f, 17.6569f, 7.3432f)
                curveTo(16.1566f, 5.8429f, 14.1217f, 5.0f, 12.0f, 5.0f)
                curveTo(9.8783f, 5.0f, 7.8434f, 5.8429f, 6.3432f, 7.3432f)
                curveTo(4.8429f, 8.8434f, 4.0f, 10.8783f, 4.0f, 13.0f)
                curveTo(4.0f, 15.1217f, 4.8429f, 17.1566f, 6.3432f, 18.6569f)
                curveTo(7.8434f, 20.1571f, 9.8783f, 21.0f, 12.0f, 21.0f)
                close()
            }
        }
            .build()
        return _alarm!!
    }

private var _alarm: ImageVector? = null
