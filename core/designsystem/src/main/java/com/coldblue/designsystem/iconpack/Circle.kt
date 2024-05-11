package com.coldblue.designsystem.iconpack

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import com.coldblue.designsystem.IconPack

public val IconPack.Circle: ImageVector
    get() {
        if (_circle != null) {
            return _circle!!
        }
        _circle = materialIcon(name = "Circle") {
            materialPath {
                moveTo(12.0f, 2.0f)
                curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
                reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
                close()

            }
        }
        return _circle!!
    }

private var _circle: ImageVector? = null