package com.coldblue.designsystem.iconpack

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

public val IconPack.Question: ImageVector
    get() {
        if (_question != null) {
            return _question!!
        }
        _question = Builder(name = "Question", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.9004f, 8.0795f)
                curveTo(7.9004f, 3.3068f, 15.4004f, 3.3068f, 15.4004f, 8.0795f)
                curveTo(15.4004f, 11.4886f, 11.9913f, 10.8067f, 11.9913f, 14.8976f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 19.01f)
                lineTo(12.01f, 18.9989f)
            }
        }
        .build()
        return _question!!
    }

private var _question: ImageVector? = null
