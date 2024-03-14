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

public val IconPack.ZoomIn: ImageVector
    get() {
        if (_zoomin != null) {
            return _zoomin!!
        }
        _zoomin = Builder(name = "ZoomIn", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.0f, 16.0f)
                lineToRelative(-2.0f, -0.0f)
                lineToRelative(-0.0f, -16.0f)
                lineToRelative(2.0f, -0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.0f, 9.0f)
                lineToRelative(0.0f, -2.0f)
                lineToRelative(16.0f, -0.0f)
                lineToRelative(0.0f, 2.0f)
                close()
            }
        }
        .build()
        return _zoomin!!
    }

private var _zoomin: ImageVector? = null
