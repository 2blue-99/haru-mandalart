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

public val IconPack.Good: ImageVector
    get() {
        if (_good != null) {
            return _good!!
        }
        _good = Builder(name = "Good", defaultWidth = 96.0.dp, defaultHeight = 96.0.dp,
                viewportWidth = 96.0f, viewportHeight = 96.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(78.0f, 24.0f)
                horizontalLineTo(71.8828f)
                curveTo(71.2559f, 12.82f, 67.2773f, 0.0f, 42.0f, 0.0f)
                arcToRelative(5.9966f, 5.9966f, 0.0f, false, false, -6.0f, 6.0f)
                curveToRelative(0.0f, 10.8809f, -0.1128f, 20.3687f, -8.6917f, 30.0f)
                horizontalLineTo(6.0f)
                arcToRelative(5.9966f, 5.9966f, 0.0f, false, false, -6.0f, 6.0f)
                verticalLineTo(90.0f)
                arcToRelative(5.9966f, 5.9966f, 0.0f, false, false, 6.0f, 6.0f)
                horizontalLineTo(78.0f)
                arcTo(18.02f, 18.02f, 0.0f, false, false, 96.0f, 78.0f)
                verticalLineTo(42.0f)
                arcTo(18.02f, 18.02f, 0.0f, false, false, 78.0f, 24.0f)
                close()
                moveTo(12.0f, 48.0f)
                horizontalLineTo(24.0f)
                verticalLineTo(84.0f)
                horizontalLineTo(12.0f)
                close()
                moveTo(84.0f, 78.0f)
                arcToRelative(6.0078f, 6.0078f, 0.0f, false, true, -6.0f, 6.0f)
                horizontalLineTo(36.0f)
                verticalLineTo(44.4023f)
                curveToRelative(9.9258f, -10.8867f, 11.6426f, -21.9257f, 11.9355f, -32.121f)
                curveTo(60.0f, 13.5938f, 60.0f, 19.5117f, 60.0f, 30.0f)
                arcToRelative(5.9966f, 5.9966f, 0.0f, false, false, 6.0f, 6.0f)
                horizontalLineTo(78.0f)
                arcToRelative(6.0078f, 6.0078f, 0.0f, false, true, 6.0f, 6.0f)
                close()
            }
        }
        .build()
        return _good!!
    }

private var _good: ImageVector? = null
