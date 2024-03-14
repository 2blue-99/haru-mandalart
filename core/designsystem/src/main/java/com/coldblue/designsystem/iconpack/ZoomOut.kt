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

public val IconPack.ZoomOut: ImageVector
    get() {
        if (_zoomout != null) {
            return _zoomout!!
        }
        _zoomout = Builder(name = "ZoomOut", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.3984f, 6.4f)
                lineToRelative(-1.6f, -0.0f)
                lineToRelative(-0.0f, -6.4f)
                lineToRelative(1.6f, -0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.3984f, 4.8f)
                lineToRelative(-0.0f, 1.6f)
                lineToRelative(-6.4f, 0.0f)
                lineToRelative(-0.0f, -1.6f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.5996f, 6.4f)
                lineToRelative(0.0f, -1.6f)
                lineToRelative(6.4f, -0.0f)
                lineToRelative(0.0f, 1.6f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.1992f, 6.4f)
                lineToRelative(-1.6f, -0.0f)
                lineToRelative(-0.0f, -6.4f)
                lineToRelative(1.6f, -0.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.5996f, 9.6f)
                horizontalLineToRelative(1.6f)
                verticalLineToRelative(6.4f)
                horizontalLineToRelative(-1.6f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.5996f, 11.2f)
                lineToRelative(0.0f, -1.6f)
                lineToRelative(6.4f, -0.0f)
                lineToRelative(0.0f, 1.6f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(6.3994f, 9.6f)
                lineToRelative(-0.0f, 1.6f)
                lineToRelative(-6.4f, 0.0f)
                lineToRelative(-0.0f, -1.6f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(4.7998f, 9.6f)
                horizontalLineToRelative(1.6f)
                verticalLineToRelative(6.4f)
                horizontalLineToRelative(-1.6f)
                close()
            }
        }
        .build()
        return _zoomout!!
    }

private var _zoomout: ImageVector? = null
