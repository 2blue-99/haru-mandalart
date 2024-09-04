package com.coldblue.designsystem.iconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.coldblue.designsystem.IconPack

public val IconPack.TutorialArrow: ImageVector
    get() {
        if (_tutorialArrow != null) {
            return _tutorialArrow!!
        }
        _tutorialArrow = Builder(name = "TutorialArrow", defaultWidth = 24.0.dp, defaultHeight =
                47.0.dp, viewportWidth = 24.0f, viewportHeight = 47.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(10.0225f, 46.0f)
                curveTo(5.672f, 42.0018f, -3.2383f, 32.9869f, 3.3483f, 27.2898f)
                curveTo(7.1943f, 23.9632f, 15.5629f, 26.4209f, 18.4843f, 29.5577f)
                curveTo(19.2365f, 30.3654f, 19.2373f, 31.268f, 18.0076f, 31.7998f)
                curveTo(15.5068f, 32.8814f, 12.0221f, 31.3318f, 10.4098f, 29.7123f)
                curveTo(4.0879f, 23.3622f, 9.751f, 13.3703f, 15.1088f, 10.0f)
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.5961f, 5.3531f)
                curveTo(18.3209f, 5.0939f, 19.0546f, 5.7165f, 18.9167f, 6.4738f)
                lineTo(17.6755f, 13.29f)
                curveTo(17.5375f, 14.0473f, 16.6315f, 14.3714f, 16.0446f, 13.8732f)
                lineTo(10.7622f, 9.3903f)
                curveTo(10.1753f, 8.8922f, 10.3477f, 7.9455f, 11.0726f, 7.6862f)
                lineTo(17.5961f, 5.3531f)
                close()
            }
        }
        .build()
        return _tutorialArrow!!
    }

private var _tutorialArrow: ImageVector? = null
