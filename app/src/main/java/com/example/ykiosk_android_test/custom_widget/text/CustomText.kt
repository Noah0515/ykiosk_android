package com.example.ykiosk_android_test.custom_widget.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme
import com.example.ykiosk_android_test.ui.theme.*

@Composable
fun SmallText1(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 4.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun SmallText2(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 6.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun SmallText3(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 8.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun SmallText4(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 10.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun MediumText1(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 12.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun MediumText2(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 16.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun MediumText3(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 20.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun MediumText4(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 24.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun LargeText1(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 28.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun LargeText2(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 34.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun LargeText3(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 40.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}

@Composable
fun LargeText4(text : String, modifier : Modifier = Modifier, color : Color = Dark2) {
    Text(
        text = text,
        fontSize = 46.sp,
        modifier = Modifier,
        textAlign = TextAlign.Center,
        style = AppTypography.bodyMedium,
        color = color
    )
}


@Composable
@Preview
fun NormalText1Prev() {
    Ykiosk_android_testTheme {
        //NormalText1("텍스트1")
        MediumText3("텍스트2")
    }
}

