package com.example.ykiosk_android_test.custom_widget.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ykiosk_android_test.custom_widget.customBorder
import com.example.ykiosk_android_test.custom_widget.text.LargeText1
import com.example.ykiosk_android_test.custom_widget.text.LargeText2
import com.example.ykiosk_android_test.custom_widget.text.LargeText3
import com.example.ykiosk_android_test.custom_widget.text.LargeText4
import com.example.ykiosk_android_test.custom_widget.text.MediumText3
import com.example.ykiosk_android_test.custom_widget.text.MediumText4
import com.example.ykiosk_android_test.ui.theme.Dark1
import com.example.ykiosk_android_test.ui.theme.Dark3
import com.example.ykiosk_android_test.ui.theme.DawnMist
import com.example.ykiosk_android_test.ui.theme.WhiteSemiLight
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme

@Composable
fun Button1(text : String = "Button1", modifier: Modifier = Modifier, onclick : () -> Unit) {
    Button(
        onClick = onclick,
        modifier = modifier
            .widthIn(96.dp)
            .heightIn(48.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun Button3(text : String = "Button1", modifier: Modifier = Modifier, onclick : () -> Unit) {
    Button(
        onClick = onclick,
        modifier = modifier
            .widthIn(96.dp)
            .heightIn(48.dp)
    ) {
        LargeText3(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun CategoryButton(text : String, modifier: Modifier = Modifier, onclick: () -> Unit) {
    Button(
        onClick = onclick,
        modifier = modifier
            .sizeIn(
                minWidth = 420.dp, minHeight = 140.dp,
                maxWidth = 600.dp, maxHeight = 210.dp
            ),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Dark3,
            contentColor = Dark1
        )
    ) {
        LargeText4(
            text = text,
            modifier = modifier,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun GroupButton(text : String, modifier : Modifier = Modifier, onclick: () -> Unit) {
    Button(
        onClick = onclick,
        modifier = modifier
            .customBorder(borderWidth = 8.dp, top = true)
            .sizeIn(
            minWidth = 140.dp, minHeight = 100.dp,
            maxWidth = 210.dp, maxHeight = 150.dp
        ),
        shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DawnMist,
            contentColor = Dark1
        )

    ) {
        LargeText2(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    Ykiosk_android_testTheme {
        //Button1(onclick = {})
        //CategoryButton("음식") { }
        GroupButton("커피") { }
    }
}