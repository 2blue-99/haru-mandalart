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

public val IconPack.Calendar: ImageVector
    get() {
        if (_calendar != null) {
            return _calendar!!
        }
        _calendar = Builder(name = "Calendar-check-01", defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(21.0f, 10.0f)
                horizontalLineTo(3.0f)
                moveTo(16.0f, 2.0f)
                verticalLineTo(6.0f)
                moveTo(8.0f, 2.0f)
                verticalLineTo(6.0f)
                moveTo(9.0f, 16.0f)
                lineTo(11.0f, 18.0f)
                lineTo(15.5f, 13.5f)
                moveTo(7.8f, 22.0f)
                horizontalLineTo(16.2f)
                curveTo(17.8802f, 22.0f, 18.7202f, 22.0f, 19.362f, 21.673f)
                curveTo(19.9265f, 21.3854f, 20.3854f, 20.9265f, 20.673f, 20.362f)
                curveTo(21.0f, 19.7202f, 21.0f, 18.8802f, 21.0f, 17.2f)
                verticalLineTo(8.8f)
                curveTo(21.0f, 7.1198f, 21.0f, 6.2798f, 20.673f, 5.638f)
                curveTo(20.3854f, 5.0735f, 19.9265f, 4.6146f, 19.362f, 4.327f)
                curveTo(18.7202f, 4.0f, 17.8802f, 4.0f, 16.2f, 4.0f)
                horizontalLineTo(7.8f)
                curveTo(6.1198f, 4.0f, 5.2798f, 4.0f, 4.638f, 4.327f)
                curveTo(4.0735f, 4.6146f, 3.6146f, 5.0735f, 3.327f, 5.638f)
                curveTo(3.0f, 6.2798f, 3.0f, 7.1198f, 3.0f, 8.8f)
                verticalLineTo(17.2f)
                curveTo(3.0f, 18.8802f, 3.0f, 19.7202f, 3.327f, 20.362f)
                curveTo(3.6146f, 20.9265f, 4.0735f, 21.3854f, 4.638f, 21.673f)
                curveTo(5.2798f, 22.0f, 6.1198f, 22.0f, 7.8f, 22.0f)
                close()
            }
        }
        .build()
        return _calendar!!
    }

private var _calendar: ImageVector? = null
