package com.example.ykiosk_android_test.custom_widget
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ykiosk_android_test.ui.theme.Dark1

fun Modifier.customBorder(borderWidth : Dp = 4.dp, borderColor : Color = Dark1, start : Boolean = false, top : Boolean = false, end : Boolean = false, bottom : Boolean = false): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = borderWidth.toPx();
        if(start) {
            drawLine(
                color = borderColor,
                start = Offset(0f + strokePx/2, 0f),
                end = Offset(0f + strokePx/2, size.height)
            )
        }

        if(top) {
            drawLine(
                color = borderColor,
                start = Offset(0f, 0f + strokePx/2),
                end = Offset(size.width, 0f + strokePx/2)
            )
        }

        if(end) {
            drawLine(
                color = borderColor,
                start = Offset(size.width - strokePx/2, 0f),
                end = Offset(size.width - strokePx/2, size.height)
            )
        }

        if(bottom) {
            drawLine(
                color = borderColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height)
            )
        }

    }
)