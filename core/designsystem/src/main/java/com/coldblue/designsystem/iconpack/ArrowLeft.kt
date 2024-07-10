package com.coldblue.designsystem.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack

public val IconPack.ArrowLeft: ImageVector
    get() {
        if (_arrowLeft != null) {
            return _arrowLeft!!
        }
        _arrowLeft = Builder(name = "ArrowLeft", defaultWidth = 512.0.dp, defaultHeight = 512.0.dp,
                viewportWidth = 512.0f, viewportHeight = 512.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(353.0f, 450.0f)
                arcToRelative(15.0f, 15.0f, 0.0f, false, true, -10.61f, -4.39f)
                lineTo(157.5f, 260.71f)
                arcToRelative(15.0f, 15.0f, 0.0f, false, true, 0.0f, -21.21f)
                lineTo(342.39f, 54.6f)
                arcToRelative(15.0f, 15.0f, 0.0f, true, true, 21.22f, 21.21f)
                lineTo(189.32f, 250.1f)
                lineTo(363.61f, 424.39f)
                arcTo(15.0f, 15.0f, 0.0f, false, true, 353.0f, 450.0f)
                close()
            }
        }
        .build()
        return _arrowLeft!!
    }

private var _arrowLeft: ImageVector? = null
