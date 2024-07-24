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

public val IconPack.Check: ImageVector
    get() {
        if (_check != null) {
            return _check!!
        }
        _check = Builder(name = "Check", defaultWidth = 32.0.dp, defaultHeight = 32.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF101820)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(16.0f, 31.0f)
                arcTo(15.0f, 15.0f, 0.0f, true, true, 31.0f, 16.0f)
                arcTo(15.0f, 15.0f, 0.0f, false, true, 16.0f, 31.0f)
                close()
                moveTo(16.0f, 3.0f)
                arcTo(13.0f, 13.0f, 0.0f, true, false, 29.0f, 16.0f)
                arcTo(13.0f, 13.0f, 0.0f, false, false, 16.0f, 3.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF101820)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.67f, 22.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, -0.73f, -0.32f)
                lineToRelative(-4.67f, -5.0f)
                arcToRelative(1.0f, 1.0f, 0.0f, false, true, 1.46f, -1.36f)
                lineToRelative(3.94f, 4.21f)
                lineToRelative(8.6f, -9.21f)
                arcToRelative(1.0f, 1.0f, 0.0f, true, true, 1.46f, 1.36f)
                lineToRelative(-9.33f, 10.0f)
                arcTo(1.0f, 1.0f, 0.0f, false, true, 13.67f, 22.0f)
                close()
            }
        }
        .build()
        return _check!!
    }

private var _check: ImageVector? = null
