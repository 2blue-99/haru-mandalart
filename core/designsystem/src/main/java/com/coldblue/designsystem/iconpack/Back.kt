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

public val IconPack.Back: ImageVector
    get() {
        if (_back != null) {
            return _back!!
        }
        _back = Builder(name = "Back", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.6f, 10.9f)
                curveTo(13.7f, 11.0f, 13.9f, 11.0f, 14.0f, 11.0f)
                horizontalLineToRelative(3.0f)
                curveToRelative(0.6f, 0.0f, 1.0f, -0.4f, 1.0f, -1.0f)
                reflectiveCurveToRelative(-0.4f, -1.0f, -1.0f, -1.0f)
                horizontalLineToRelative(-0.6f)
                lineToRelative(3.3f, -3.3f)
                curveToRelative(0.4f, -0.4f, 0.4f, -1.0f, 0.0f, -1.4f)
                reflectiveCurveToRelative(-1.0f, -0.4f, -1.4f, 0.0f)
                lineTo(15.0f, 7.6f)
                verticalLineTo(7.0f)
                curveToRelative(0.0f, -0.6f, -0.4f, -1.0f, -1.0f, -1.0f)
                reflectiveCurveToRelative(-1.0f, 0.4f, -1.0f, 1.0f)
                verticalLineToRelative(3.0f)
                curveToRelative(0.0f, 0.1f, 0.0f, 0.3f, 0.1f, 0.4f)
                curveTo(13.2f, 10.6f, 13.4f, 10.8f, 13.6f, 10.9f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.0f, 20.0f)
                curveToRelative(0.3f, 0.0f, 0.5f, -0.1f, 0.7f, -0.3f)
                lineTo(9.0f, 16.4f)
                verticalLineTo(17.0f)
                curveToRelative(0.0f, 0.6f, 0.4f, 1.0f, 1.0f, 1.0f)
                reflectiveCurveToRelative(1.0f, -0.4f, 1.0f, -1.0f)
                verticalLineToRelative(-3.0f)
                curveToRelative(0.0f, -0.1f, 0.0f, -0.3f, -0.1f, -0.4f)
                curveToRelative(-0.1f, -0.2f, -0.3f, -0.4f, -0.5f, -0.5f)
                curveTo(10.3f, 13.0f, 10.1f, 13.0f, 10.0f, 13.0f)
                horizontalLineTo(7.0f)
                curveToRelative(-0.6f, 0.0f, -1.0f, 0.4f, -1.0f, 1.0f)
                reflectiveCurveToRelative(0.4f, 1.0f, 1.0f, 1.0f)
                horizontalLineToRelative(0.6f)
                lineToRelative(-3.3f, 3.3f)
                curveToRelative(-0.4f, 0.4f, -0.4f, 1.0f, 0.0f, 1.4f)
                curveTo(4.5f, 19.9f, 4.7f, 20.0f, 5.0f, 20.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.3f, 16.7f)
                curveToRelative(0.2f, 0.2f, 0.5f, 0.3f, 0.7f, 0.3f)
                reflectiveCurveToRelative(0.5f, -0.1f, 0.7f, -0.3f)
                curveToRelative(0.4f, -0.4f, 0.4f, -1.0f, 0.0f, -1.4f)
                lineToRelative(-8.0f, -8.0f)
                curveToRelative(-0.4f, -0.4f, -1.0f, -0.4f, -1.4f, 0.0f)
                reflectiveCurveToRelative(-0.4f, 1.0f, 0.0f, 1.4f)
                lineTo(15.3f, 16.7f)
                close()
            }
        }
        .build()
        return _back!!
    }

private var _back: ImageVector? = null
