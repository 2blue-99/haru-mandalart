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

public val IconPack.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home = Builder(name = "Home", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(20.0298f, 13.0f)
                curveTo(19.4798f, 13.0f, 19.0298f, 13.45f, 19.0298f, 14.0f)
                verticalLineTo(15.35f)
                curveTo(19.0298f, 17.36f, 17.3898f, 19.0f, 15.3798f, 19.0f)
                horizontalLineTo(8.6598f)
                curveTo(6.6498f, 19.0f, 5.0098f, 17.36f, 5.0098f, 15.35f)
                verticalLineTo(8.63f)
                curveTo(5.0098f, 6.62f, 6.6498f, 4.98f, 8.6598f, 4.98f)
                horizontalLineTo(9.9998f)
                curveTo(10.5498f, 4.98f, 10.9998f, 4.53f, 10.9998f, 3.98f)
                curveTo(10.9998f, 3.43f, 10.5498f, 2.98f, 9.9998f, 2.98f)
                horizontalLineTo(8.6598f)
                curveTo(5.5398f, 2.98f, 3.0098f, 5.52f, 3.0098f, 8.63f)
                verticalLineTo(15.35f)
                curveTo(3.0098f, 18.47f, 5.5498f, 21.0f, 8.6598f, 21.0f)
                horizontalLineTo(15.3798f)
                curveTo(18.4998f, 21.0f, 21.0298f, 18.46f, 21.0298f, 15.35f)
                verticalLineTo(14.0f)
                curveTo(21.0298f, 13.45f, 20.5798f, 13.0f, 20.0298f, 13.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF432ED1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(7.2896f, 10.2999f)
                curveTo(6.8996f, 10.6899f, 6.8996f, 11.32f, 7.2896f, 11.71f)
                lineTo(10.5496f, 14.97f)
                curveTo(10.9096f, 15.33f, 11.3996f, 15.5299f, 11.9096f, 15.5299f)
                curveTo(11.9496f, 15.5299f, 11.9896f, 15.5299f, 12.0296f, 15.5299f)
                curveTo(12.5796f, 15.4999f, 13.0896f, 15.2299f, 13.4296f, 14.7899f)
                lineTo(19.7796f, 6.62f)
                curveTo(20.1196f, 6.18f, 20.0396f, 5.56f, 19.6096f, 5.22f)
                curveTo(19.1696f, 4.88f, 18.5496f, 4.96f, 18.2096f, 5.39f)
                lineTo(11.9096f, 13.5f)
                lineTo(8.7096f, 10.2999f)
                curveTo(8.3196f, 9.9099f, 7.6896f, 9.9099f, 7.2996f, 10.2999f)
                horizontalLineTo(7.2896f)
                close()
            }
        }
        .build()
        return _home!!
    }

private var _home: ImageVector? = null
